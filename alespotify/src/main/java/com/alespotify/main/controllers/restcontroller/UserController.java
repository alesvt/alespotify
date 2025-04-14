package com.alespotify.main.controllers.restcontroller;

import com.alespotify.main.models.entities.User;
import com.alespotify.main.repository.PlaylistRepository;
import com.alespotify.main.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final PlaylistRepository playlistRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository, PlaylistRepository playlistRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.playlistRepository = playlistRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @PostMapping(consumes = "application/json")
    public User createUser(@RequestBody User user) {
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        return userRepository.save(user);
    }

}
