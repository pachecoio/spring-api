package dev.thiagopacheco.springbootapi.controllers;

import dev.thiagopacheco.springbootapi.adapters.UserAdapter;
import dev.thiagopacheco.springbootapi.models.User;
import dev.thiagopacheco.springbootapi.repositories.UserRepository;
import dev.thiagopacheco.springbootapi.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api")
public class UserController {

    private final JwtUtils jwtUtils;
    @Autowired
    private UserAdapter adapter;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    UserController() {
        this.jwtUtils = new JwtUtils();
    }

    @GetMapping("/user")
    public @ResponseBody
    Iterable<User> getAll(@RequestParam(value = "email", required = false) String email) {
        return adapter.filter(email);
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity<User> create(@RequestBody User input) {
        User user = adapter.create(input);
        return ResponseEntity.status(201).body(user);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity signup(@RequestBody User input) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(input.getEmail());
        final String jwt = jwtUtils.generateToken(userDetails);
        return ResponseEntity.status(201).body(jwt);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResponseEntity<User> get(@PathVariable("id") Integer id) {
        Optional<User> user = adapter.get(id);

        return user.map(value -> ResponseEntity.status(200).body(value)).orElseGet(() -> ResponseEntity.status(404).body(null));
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT, produces = "application/json")
    @ResponseBody
    public ResponseEntity<User> update(@PathVariable("id") Integer id, @RequestBody User input) {
        if (!adapter.get(id).isPresent()) return ResponseEntity.status(404).body(null);
        input.setId(id);
        User user = adapter.update(input);
        return ResponseEntity.status(200).body(user);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public ResponseEntity<User> delete(@PathVariable("id") Integer id) {
        if (!adapter.get(id).isPresent()) return ResponseEntity.status(404).body(null);
        adapter.delete(id);
        return ResponseEntity.status(202).body(null);
    }

}
