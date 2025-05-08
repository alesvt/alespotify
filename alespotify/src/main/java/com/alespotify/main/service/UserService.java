package com.alespotify.main.service;

import com.alespotify.main.models.entities.Favorito;
import com.alespotify.main.models.entities.Usuario;
import com.alespotify.main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {



    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario registerUser(Usuario user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setFavoritos(new Favorito());
        user.setName(user.getName());
        System.out.println(user);
        return userRepository.save(user);
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Usuario> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            throw new UsernameNotFoundException("User not found with email : " + email);
        } else {

            return org.springframework.security.core.userdetails.User
                    .withUsername(user.get().getEmail())
                    .password(user.get().getPassword())
                    .authorities(Collections.emptyList())
                    .build();
        }
    }

    public Optional<Usuario> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Usuario login(String credentials) {
        JsonParser pars = JsonParserFactory.getJsonParser();
        Map<String, Object> m = pars.parseMap(credentials);
        String email = (String) m.get("email");
        String password = (String) m.get("password");
        Optional<Usuario> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            if (passwordEncoder.matches(password, user.get().getPassword())) {
                return user.get();
            } else {
                return null;
            }
        }
        return null;
    }

}
