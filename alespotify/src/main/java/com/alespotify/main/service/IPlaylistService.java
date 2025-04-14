package com.alespotify.main.service;

import com.alespotify.main.models.entities.Playlist;

import java.util.List;

public interface IPlaylistService {
    List<Playlist> getAllPlaylists();
}
