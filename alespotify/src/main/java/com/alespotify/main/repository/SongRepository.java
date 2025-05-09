package com.alespotify.main.repository;


import com.alespotify.main.models.entities.Cancion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRepository extends JpaRepository<Cancion, Long> {
}
