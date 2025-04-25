package com.alespotify.main.util;

import com.alespotify.main.models.dto.SongArtistDTO;
import com.alespotify.main.models.dto.ArtistSoloSongNamesDTO;
import com.alespotify.main.models.dto.SongArtistInfoFromSongDTO;
import com.alespotify.main.models.entities.Artist;
import com.alespotify.main.models.entities.Song;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ArtistMapper {


    public static SongArtistDTO toSongArtistDTO(Artist artist) {
        SongArtistDTO artistDTO = new SongArtistDTO();
        artistDTO.setId(artist.getId());
        artistDTO.setDescription(artist.getDescription());
        artistDTO.setName(artist.getName());
        artistDTO.setImage(artist.getImage());
        ArrayList<SongArtistInfoFromSongDTO> songs = new ArrayList<>();
        if (artist.getSongs() != null) {
            for (Song song : artist.getSongs()) {
                SongMapper.toSongArtistInfoSongDTO(song);
                songs.add(SongMapper.toSongArtistInfoSongDTO(song));
            }
            artistDTO.setSongs(songs);
        }
        return artistDTO;
    }

   
}
