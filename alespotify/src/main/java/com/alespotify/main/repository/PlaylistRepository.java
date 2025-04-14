package com.alespotify.main.repository;

import com.alespotify.main.models.entities.Playlist;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlaylistRepository extends MongoRepository<Playlist, String> {
}
