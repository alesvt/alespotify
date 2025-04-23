package com.alespotify.main.util;

import com.alespotify.main.models.dto.CancionConArtistasReducido;
import com.alespotify.main.models.dto.PlaylistDTO;
import com.alespotify.main.models.entities.Playlist;
import com.alespotify.main.models.entities.Song;

import java.util.ArrayList;

public class PlaylistMapper {

    public static PlaylistDTO toPlaylistDTO(Playlist playlist) {
        PlaylistDTO dto = new PlaylistDTO();
        dto.setId(playlist.getId());
        dto.setName(playlist.getName());
        dto.setImage(playlist.getImage());
        dto.setPublicPlaylist(playlist.isPublicPlaylist());
        ArrayList<CancionConArtistasReducido> canciones = new ArrayList<>();
        if (playlist.getSongs() != null) {
            for (Song song : playlist.getSongs()) {
                canciones.add(SongMapper.toCancionConArtistasReducido(song));
            }
            dto.setSongs(canciones);
        }

        return dto;

    }
}
