package com.alespotify.main.service;

import com.alespotify.main.models.dto.CancionConArtistasReducido;
import com.alespotify.main.models.entities.Song;
import com.alespotify.main.repository.SongRepository;
import com.alespotify.main.util.SongMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SongServiceImpl implements ISongService {
    private final SongRepository songRepository;

    @Override
    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }

    @Override
    public List<CancionConArtistasReducido> getAllSongsArtistLessInfo() {
        return songRepository.findAll()
                .stream()
                .map(SongMapper::toCancionConArtistasReducido)
                .collect(Collectors.toList());
    }
}
