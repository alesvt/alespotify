package com.alespotify.main.util;

import com.alespotify.main.models.dto.ArtistDTO;
import com.alespotify.main.models.dto.ArtistSoloSongNamesDTO;
import com.alespotify.main.models.entities.Artist;
import com.alespotify.main.models.entities.Song;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ArtistMapper {
    public static ArtistDTO toArtistDTO(Artist artist) {
        ArtistDTO artistDTO = new ArtistDTO();
        return artistDTO;
    }

    
    public static ArtistSoloSongNamesDTO toArtistSoloSongNamesDTO(Artist artist) {
        ArtistSoloSongNamesDTO dto = new ArtistSoloSongNamesDTO();

        dto.setId(artist.getId());
        dto.setName(artist.getName());
        dto.setImage(artist.getImage());
        dto.setDescription(artist.getDescription());
        // cambiar ?? TODO
        dto.setAlbums(artist.getAlbums());
        Map<String, String> mapaCanciones = new HashMap<>();
        ArrayList<Map<String, String>> canciones = new ArrayList<>();
        if (artist.getSongs() != null) {
            for (Song s : artist.getSongs()) {
                mapaCanciones.put(s.getId(), s.getTitle());
                canciones.add(mapaCanciones);
            }
            dto.setSongs(canciones);
        }
        return dto;
    }

}
