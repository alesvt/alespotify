package com.alespotify.main.repository;

import com.alespotify.main.models.entities.Artista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtistRepository extends JpaRepository<Artista, Integer> {
    @Query("SELECT ar, SUM(c.timesPlayed) AS totalReproducciones " +
            "FROM Artista ar JOIN ar.songs c " +
            "GROUP BY ar " +
            "ORDER BY totalReproducciones DESC " +
            "LIMIT 5")
    List<Artista> findTop3Artists();
}