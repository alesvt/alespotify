package com.alespotify.main.repository;

import com.alespotify.main.models.entities.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Long> {

    @Query("SELECT a from Album a where true order by a.releaseDate desc limit 5")
    List<Album> findNewestAlbums();

}
