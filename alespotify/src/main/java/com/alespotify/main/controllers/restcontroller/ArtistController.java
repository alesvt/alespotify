package com.alespotify.main.controllers.restcontroller;

import com.alespotify.main.models.entities.Artista;
import com.alespotify.main.repository.ArtistRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/artists")
public class ArtistController {

    private final ArtistRepository artistRepository;

    public ArtistController(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @GetMapping
    public List<Artista> getArtists() {
        return artistRepository.findTop3Artists();
    }
}
