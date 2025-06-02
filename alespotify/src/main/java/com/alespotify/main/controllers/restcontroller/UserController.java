package com.alespotify.main.controllers.restcontroller;

import com.alespotify.main.models.entities.Usuario;
import com.alespotify.main.repository.UserRepository;
import com.alespotify.main.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public List<Usuario> getAllUsers() {
        return (List<Usuario>) userRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUserById(@PathVariable Integer id) {
        Optional<Usuario> user = userRepository.findById(Long.valueOf(id));
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody String credentials) {
        Usuario usuario = userService.login(credentials);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Usuario> register( Usuario usuario) {
        System.out.println(usuario);
        usuario.setCreationDate(Instant.now());
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        System.out.println(usuario.getPassword());
        Usuario usuarioCreado = userRepository.save(usuario);
        return ResponseEntity.ok(usuarioCreado);
    }
}
