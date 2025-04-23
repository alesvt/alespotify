package com.alespotify.main.controllers.restcontroller;


import com.alespotify.main.models.dto.PlaylistDTO;
import com.alespotify.main.models.entities.Playlist;
import com.alespotify.main.repository.ArtistRepository;
import com.alespotify.main.repository.PlaylistRepository;
import com.alespotify.main.repository.SongRepository;
import com.alespotify.main.service.PlaylistServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/playlists")
public class PlaylistController {

    private final PlaylistRepository playlistRepository;
    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;
    private final PlaylistServiceImpl playlistService;

    public PlaylistController(PlaylistServiceImpl playlistService, SongRepository songRepository, ArtistRepository artistRepository, PlaylistRepository playlistRepository) {
        this.songRepository = songRepository;
        this.playlistRepository = playlistRepository;
        this.artistRepository = artistRepository;
        this.playlistService = playlistService;
    }

    @GetMapping()
    public List<PlaylistDTO> getPlaylists() {
        return playlistService.getAllPlaylistsDTO();
    }

    @GetMapping("/{id}")
    public Playlist getPlaylist(@PathVariable String id) {
        return playlistRepository.findById(id).orElse(null);
    }

    @PostMapping(consumes = "application/json")
    public Playlist createPlaylist(@RequestBody Playlist playlist) {
//        if(playlist.getUser().getId() != null) {
//            System.out.println("Usuario: " + playlist.getUser().getName());
//        }
        return playlistRepository.save(playlist);
    }
}
