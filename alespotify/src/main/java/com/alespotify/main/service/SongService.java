package com.alespotify.main.service;

import com.alespotify.main.repository.SongRepository;
import com.alespotify.main.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class SongService {

    private final SongRepository songRepository;


    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

}
