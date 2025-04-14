package com.alespotify.main.repository;

import com.alespotify.main.models.entities.Artist;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepository extends MongoRepository<Artist, String> {
}
