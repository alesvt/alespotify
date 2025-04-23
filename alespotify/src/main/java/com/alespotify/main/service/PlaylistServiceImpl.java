package com.alespotify.main.service;

import com.alespotify.main.models.dto.PlaylistDTO;
import com.alespotify.main.models.entities.Playlist;
import com.alespotify.main.repository.PlaylistRepository;
import com.alespotify.main.util.PlaylistMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PlaylistServiceImpl implements IPlaylistService {

    private final PlaylistRepository playlistRepository;

    @Override
    public List<Playlist> getAllPlaylists() {
        return playlistRepository.findAll();
    }

    @Override
    public List<PlaylistDTO> getAllPlaylistsDTO(){
        List<Playlist> playlists = playlistRepository.findAll();
        return playlists.stream()
                .map(PlaylistMapper::toPlaylistDTO)
                .collect(Collectors.toList());
    }
}
