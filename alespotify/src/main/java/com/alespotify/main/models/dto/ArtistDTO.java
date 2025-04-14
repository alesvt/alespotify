package com.alespotify.main.models.dto;

import com.alespotify.main.models.entities.Song;
import lombok.Data;

import java.util.List;

@Data
public class ArtistDTO {
    private String id;
    private String name;
   // private List<NoArtistSongs> songs;
}

