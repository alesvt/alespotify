package com.alespotify.main.repository;


import com.alespotify.main.models.entities.Cancion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SongRepository extends JpaRepository<Cancion, Long> {
    @Query("SELECT c from Cancion c left join fetch c.artists where c.id = :id")
    Optional<Cancion> findByIdConArtista(@Param("id") Long id);

    @Query("SELECT c from Cancion c where true order by c.timesPlayed desc limit 15")
    List<Cancion> findByVecesReproducido();


    @Query("SELECT c from Cancion c where c.name like %:nombre%")
    Optional<List<Cancion>> findByNombre(@Param("nombre") String nombre);

}
