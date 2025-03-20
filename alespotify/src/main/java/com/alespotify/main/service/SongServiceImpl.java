package com.alespotify.main.service;

import com.alespotify.main.models.Song;
import com.alespotify.main.repository.SongRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SongServiceImpl implements ISongService {
    private final SongRepository songRepository;

    @Override
    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }
}
