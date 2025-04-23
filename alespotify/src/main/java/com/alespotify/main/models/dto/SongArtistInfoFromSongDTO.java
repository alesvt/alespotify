package com.alespotify.main.models.dto;

import lombok.Data;

import java.util.ArrayList;


/**
 * este es el dto que sale si hago:
 * api/songs
 * song {
 *     artists {
 *         nombre
 *         id
 *         _songs_ {
 *             nombre
 *             id
 *             imagen
 *             source
 *         }
 *          ESTE ^^^
 *     }
 * }
 */

@Data
public class SongArtistInfoFromSongDTO {
    private String id;
    private String title;
    private String thumbImage;
    private String source;
}

