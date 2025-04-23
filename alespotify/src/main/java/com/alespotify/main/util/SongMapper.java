package com.alespotify.main.util;

import com.alespotify.main.models.dto.*;
import com.alespotify.main.models.entities.Album;
import com.alespotify.main.models.entities.Artist;
import com.alespotify.main.models.entities.Song;

import java.util.ArrayList;

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


    public static CancionConArtistasReducido toCancionConArtistasReducido(Song song) {
        CancionConArtistasReducido dto = new CancionConArtistasReducido();
        dto.setId(song.getId());
        dto.setAlbum(song.getAlbum());
        dto.setThumbImage(song.getThumbImage());
        dto.setTitle(song.getTitle());
        dto.setSource(song.getSource());
        ArrayList<SongArtistDTO> artists = new ArrayList<>();
        if (song.getArtists() != null) {
            for (Artist ar : song.getArtists()) {
                artists.add(ArtistMapper.toSongArtistDTO(ar));
            }
            dto.setArtists(artists);
        }
        return dto;
    }


    public static SongArtistInfoFromSongDTO toSongArtistInfoSongDTO(Song song) {
        SongArtistInfoFromSongDTO dto = new SongArtistInfoFromSongDTO();
        dto.setId(song.getId());
        dto.setSource(song.getSource());
        dto.setTitle(song.getTitle());
        dto.setThumbImage(song.getThumbImage());
        return dto;
    }


}
