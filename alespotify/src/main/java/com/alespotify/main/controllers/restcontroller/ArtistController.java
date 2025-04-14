package com.alespotify.main.controllers.restcontroller;


import com.alespotify.main.models.dto.ArtistSoloSongNamesDTO;
import com.alespotify.main.models.entities.Artist;
import com.alespotify.main.repository.ArtistRepository;
import com.alespotify.main.util.ArtistMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/artists")
public class ArtistController {

    private final ArtistRepository artistRepository;

    public ArtistController(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }


    @GetMapping
    public List<ArtistSoloSongNamesDTO> findAll() {
        return artistRepository.findAll().stream()
                .map(ArtistMapper::toArtistSoloSongNamesDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ArtistSoloSongNamesDTO findById(@PathVariable String id) {
        Artist artist = artistRepository.findById(id).orElse(null);
        if (artist != null) {
            return ArtistMapper.toArtistSoloSongNamesDTO(artist);
        }
        return null;
    }

}
