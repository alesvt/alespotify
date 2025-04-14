package com.alespotify.main.repository;


import com.alespotify.main.models.entities.Song;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRepository extends MongoRepository<Song, String> {
    Song findByTitle(String title);


}
