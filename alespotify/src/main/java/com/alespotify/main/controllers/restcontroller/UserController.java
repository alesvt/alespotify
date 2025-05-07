package com.alespotify.main.controllers.restcontroller;

import com.alespotify.main.models.dto.UserAndroid;
import com.alespotify.main.models.entities.Playlist;
import com.alespotify.main.models.entities.User;
import com.alespotify.main.repository.PlaylistRepository;
import com.alespotify.main.repository.SongRepository;
import com.alespotify.main.repository.UserRepository;
import com.alespotify.main.service.UserService;
import com.alespotify.main.util.UserMapper;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final SongRepository songRepository;

    public UserController(UserRepository userRepository, PlaylistRepository playlistRepository, PasswordEncoder passwordEncoder, UserService userService, SongRepository songRepository
    ) {
        this.userRepository = userRepository;
        this.playlistRepository = playlistRepository;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.songRepository = songRepository;
    }

    @GetMapping
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @PostMapping(consumes = "application/json")
    public User createUser(@RequestBody User user) {
        System.out.println(user);
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        user.setName(user.getName());
        return userRepository.save(user);
    }

    /**
     * @param credentials
     * @return ResponseEntity with the authorised user
     */
    @PostMapping("/login")
    public ResponseEntity<User> getUserByCredentials(@RequestBody String credentials) {
        if (userService.login(credentials) != null) {
            User user = userService.login(credentials);

            // usuario correcto
            System.out.println("Login successful");
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id) {
        if (userRepository.findById(id).isPresent()) {
            User user = userRepository.findById(id).get();
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
