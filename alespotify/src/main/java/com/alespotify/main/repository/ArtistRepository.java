package com.alespotify.main.repository;

import com.alespotify.main.models.entities.Artista;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artista, Integer> {
}