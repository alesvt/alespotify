package com.alespotify.main.controllers.restcontroller;


import com.alespotify.main.repository.ArtistRepository;
import com.alespotify.main.repository.SongRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController@CrossOrigin
@RequestMapping("/api/playlists")
public class PlaylistController {

    private final PlaylistRepository playlistRepository;
    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;

    public PlaylistController()
}
