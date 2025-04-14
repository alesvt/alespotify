package com.alespotify.main.service;

import com.alespotify.main.models.dto.ArtistSoloSongNamesDTO;
import com.alespotify.main.models.entities.Artist;

import java.util.List;

public interface IArtistService {
    List<Artist> getAllArtists();

    List<ArtistSoloSongNamesDTO> getAllArtistsSoloSongName();
}
