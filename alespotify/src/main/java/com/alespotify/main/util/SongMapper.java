package com.alespotify.main.util;

import com.alespotify.main.models.dto.SongSoloArtistNamesDTO;
import com.alespotify.main.models.dto.SongDTO;
import com.alespotify.main.models.entities.Artist;
import com.alespotify.main.models.entities.Song;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SongMapper {
    public static SongDTO toSongDTO(Song song) {
        SongDTO dto = new SongDTO();
        dto.setId(song.getId());
        dto.setGenres(song.getGenres());
        dto.setLength(song.getLength());
        dto.setAddedToPlaylists(song.getAddedToPlaylists());
        dto.setSource(song.getSource());
        dto.setTitle(song.getTitle());
        dto.setThumbImage(song.getThumbImage());
        dto.setTimesPlayed(song.getTimesPlayed());
        dto.setArtists(song.getArtists());
        if (song.getAlbum() != null) {
            dto.setAlbum(song.getAlbum());
        }
        if (song.getArtists() != null) {
            dto.setArtists(song.getArtists());
        }
        return dto;
    }

    public static SongSoloArtistNamesDTO toSoloArtistNamesDTO(Song song) {
        SongSoloArtistNamesDTO dto = new SongSoloArtistNamesDTO();
        dto.setId(song.getId());
        dto.setGenres(song.getGenres());
        dto.setLength(song.getLength());
        dto.setAddedToPlaylists(song.getAddedToPlaylists());
        dto.setSource(song.getSource());
        dto.setTitle(song.getTitle());
        dto.setThumbImage(song.getThumbImage());
        dto.setTimesPlayed(song.getTimesPlayed());
        if (song.getAlbum() != null) {
            dto.setNombreAlbum(song.getAlbum().getName());
        }
        Map<String, String> mapaArtistas = new HashMap<>();
        ArrayList<Map<String, String>> artistas = new ArrayList<>();
        if (song.getArtists() != null) {
            for (Artist artist : song.getArtists()) {
                mapaArtistas.put(artist.getId(), artist.getName());
                artistas.add(mapaArtistas);
            }
            dto.setArtists(artistas);
        }
        return dto;
    }


}
