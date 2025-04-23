package com.alespotify.main.service;

import com.alespotify.main.models.dto.CancionConArtistasReducido;
import com.alespotify.main.models.entities.Song;

import java.util.List;

public interface ISongService {
    List<Song> getAllSongs();
    List<CancionConArtistasReducido> getAllSongsArtistLessInfo();
}
