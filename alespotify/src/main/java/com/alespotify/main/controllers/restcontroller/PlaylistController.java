package com.alespotify.main.controllers.restcontroller;

import com.alespotify.main.models.entities.Playlist;
import com.alespotify.main.models.entities.Usuario;
import com.alespotify.main.repository.PlaylistRepository;
import com.alespotify.main.repository.UserRepository;
import lombok.Data;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/playlists")
public class PlaylistController {

    private final PlaylistRepository playlistRepository;
    private final UserRepository userRepository;

    public PlaylistController(PlaylistRepository playlistRepository, UserRepository userRepository) {
        this.playlistRepository = playlistRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<Playlist> getAllPlaylists() {
        return playlistRepository.findAll();
    }

    @GetMapping("/public")
    public List<Playlist> getPublicPlaylists() {
        return playlistRepository.findPublicPlaylists();
    }

    @PostMapping("/new")
    public ResponseEntity<Playlist> createPlaylist(@RequestParam String nombre, @RequestParam String userId) {
        System.out.println(nombre + " " + userId);
        Optional<Usuario> user = userRepository.findById(Long.valueOf(userId));
        Playlist nplaylist = new Playlist();
        if (user.isPresent()) {
            nplaylist.setIsPublic(true);
            nplaylist.setCreationDate(Instant.now());
            nplaylist.setUpdateDate(nplaylist.getCreationDate());
            nplaylist.setNombre(nombre);
            nplaylist.setUser(user.get());
            playlistRepository.save(nplaylist);
            return ResponseEntity.ok(nplaylist);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Playlist> getPlaylist(@PathVariable Long id) {
        Optional<Playlist> playlist = playlistRepository.findById(id);
        return playlist.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user")
    public List<Playlist> getUserPlaylists(@RequestParam String userId) {
        Optional<Usuario> user = userRepository.findById(Long.valueOf(userId));
        if (user.isPresent()) {
            return playlistRepository.findByUserId(Long.valueOf(userId));
        } else return null;
    }
}

