package com.alespotify.main.repository;

import com.alespotify.main.models.entities.Favorito;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoritoRepository extends JpaRepository<Favorito, Integer> {
}