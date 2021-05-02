package dev.thiagopacheco.springbootapi.adapters;

import dev.thiagopacheco.springbootapi.helpers.Utils;
import dev.thiagopacheco.springbootapi.models.User;
import dev.thiagopacheco.springbootapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserAdapter {

    private final BCryptPasswordEncoder encoder;

    @Autowired
    private UserRepository repository;

    public UserAdapter() {
        this.encoder = Utils.bCryptPasswordEncoder();
    }

    public User create(User data) {
        data.setPassword(encoder.encode(data.getPassword()));
        return repository.save(data);
    }

    public Optional<User> get(int id) {
        return repository.findById(id);
    }

    public User getByEmail(String email) {
        return repository.findByEmail(email);
    }

    public Iterable<User> filter(String email) {
        return repository.findAll();
    }

    public User update(User data) {
        return repository.save(data);
    }

    public void delete(int id) {
        repository.deleteById(id);
    }

}
