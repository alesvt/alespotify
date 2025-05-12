package com.alespotify.main.repository;

import com.alespotify.main.models.entities.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    @Query("SELECT p FROM Playlist p WHERE p.user.id = :usuarioId or p.user.id = 1")
    List<Playlist> findByUserId(@Param("usuarioId") Long usuarioId);

    @Query("Select p from Playlist p where p.isPublic = true")
    List<Playlist> findPublicPlaylists();
}
