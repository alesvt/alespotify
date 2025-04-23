package com.alespotify.main.controllers.restcontroller;

import com.alespotify.main.models.entities.Playlist;
import com.alespotify.main.models.entities.User;
import com.alespotify.main.repository.PlaylistRepository;
import com.alespotify.main.repository.UserRepository;
import com.alespotify.main.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final PlaylistRepository playlistRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public UserController(UserRepository userRepository, PlaylistRepository playlistRepository, PasswordEncoder passwordEncoder, UserService userService) {
        this.userRepository = userRepository;
        this.playlistRepository = playlistRepository;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
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

    // todo login method

    /**
     *
     * @param credentials
     * @return the authenticated user
     */
    @GetMapping("/{credentials}")
    public User getUser(@RequestBody String credentials) {
         return userService.login(credentials);
        //return userRepository.findByEmail(credentials);
    }

}
