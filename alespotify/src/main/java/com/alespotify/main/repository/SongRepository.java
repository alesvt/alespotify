package com.alespotify.main.repository;


import com.alespotify.main.models.entities.Cancion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SongRepository extends JpaRepository<Cancion, Long> {
    @Query("SELECT c from Cancion c left join fetch c.artists where c.id = :id")
    Optional<Cancion> findByIdConArtista(@Param("id") Long id);
}
