package com.alespotify.main.service;

import com.alespotify.main.models.entities.Playlist;
import com.alespotify.main.repository.PlaylistRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PlaylistServiceImpl implements IPlaylistService {

    private final PlaylistRepository playlistRepository;

    @Override
    public List<Playlist> getAllPlaylists() {
        return playlistRepository.findAll();
    }
}
