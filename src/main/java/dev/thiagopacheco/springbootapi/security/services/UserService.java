package dev.thiagopacheco.springbootapi.security.services;


import dev.thiagopacheco.springbootapi.adapters.UserAdapter;
import dev.thiagopacheco.springbootapi.helpers.Utils;
import dev.thiagopacheco.springbootapi.models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService {

    private final UserAdapter adapter;
    private final BCryptPasswordEncoder decoder;

    UserService() {
        this.adapter = new UserAdapter();
        this.decoder = Utils.bCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return new org.springframework.security.core.userdetails.User(
                "hi@pacheco.io",
                "Senha123!",
                new ArrayList<>()
        );
    }
}
