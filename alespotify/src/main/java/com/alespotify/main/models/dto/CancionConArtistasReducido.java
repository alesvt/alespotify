package com.alespotify.main.models.dto;

import com.alespotify.main.models.entities.Album;
import lombok.Data;

import java.util.List;

/**
 * Esto es una cancion cuya informacion será
 *  nombre
 *  id
 *  cosas de cancion
 *  artistas (SongArtistDTO){
 *      nombre
 *      id
 *      imagen
 *      canciones (SongArtistInfoFromSongDTO) {
 *          nombre
 *          id
 *          imagen
 *      }
 *  }
 */

@Data
public class CancionConArtistasReducido {
    private String id;
    private String title;
    private String thumbImage;
    private String source;
    private Album album;
    private List<SongArtistDTO> artists;
}
