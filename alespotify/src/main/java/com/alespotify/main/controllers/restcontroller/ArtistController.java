package com.alespotify.main.controllers.restcontroller;


import com.alespotify.main.models.dto.SongArtistDTO;
import com.alespotify.main.models.dto.ArtistSoloSongNamesDTO;
import com.alespotify.main.models.entities.Artist;
import com.alespotify.main.repository.ArtistRepository;
import com.alespotify.main.service.ArtistServiceImpl;
import com.alespotify.main.util.ArtistMapper;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/artists")
public class ArtistController {

    private final ArtistRepository artistRepository;
    private final ArtistServiceImpl artistService;


    public ArtistController(ArtistRepository artistRepository, ArtistServiceImpl artistService) {
        this.artistRepository = artistRepository;
        this.artistService = artistService;
    }


    @GetMapping
    public ResponseEntity<List<SongArtistDTO>> findAll() {
        return ResponseEntity.ok(artistService.getAllArtistsDTO());
    }

    @GetMapping("/{id}")
    public Artist findById(@PathVariable String id) {

        return artistRepository.findById(id).orElse(null);
    }

}
