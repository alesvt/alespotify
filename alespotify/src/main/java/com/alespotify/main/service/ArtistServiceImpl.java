package com.alespotify.main.service;

import com.alespotify.main.models.dto.SongArtistDTO;
import com.alespotify.main.models.dto.ArtistSoloSongNamesDTO;
import com.alespotify.main.models.entities.Artist;
import com.alespotify.main.repository.ArtistRepository;
import com.alespotify.main.util.ArtistMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ArtistServiceImpl implements IArtistService {
    private final ArtistRepository artistRepository;


    @Override
    public List<Artist> getAllArtists() {
        return artistRepository.findAll();
    }


    @Override
    public List<SongArtistDTO> getAllArtistsDTO() {
        List<Artist> artists = artistRepository.findAll();
        return artists.stream()
                .map(ArtistMapper::toSongArtistDTO)
                .collect(Collectors.toList());
    }
}
