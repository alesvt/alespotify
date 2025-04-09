package com.alespotify.main.controllers.restcontroller;


import com.alespotify.main.models.Playlist;
import com.alespotify.main.repository.ArtistRepository;
import com.alespotify.main.repository.PlaylistRepository;
import com.alespotify.main.repository.SongRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/playlists")
public class PlaylistController {

    private final PlaylistRepository playlistRepository;
    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;

    public PlaylistController(SongRepository songRepository, ArtistRepository artistRepository, PlaylistRepository playlistRepository) {
        this.songRepository = songRepository;
        this.playlistRepository = playlistRepository;
        this.artistRepository = artistRepository;
    }

    @GetMapping()
    public List<Playlist> getPlaylists() {
        return playlistRepository.findAll();
    }

    @GetMapping("/{id}")
    public Playlist getPlaylist(@PathVariable String id) {
        return playlistRepository.findById(id).orElse(null);
    }

    @PostMapping()
    public Playlist createPlaylist(@RequestBody Playlist playlist) {
        return playlistRepository.save(playlist);
    }
}
