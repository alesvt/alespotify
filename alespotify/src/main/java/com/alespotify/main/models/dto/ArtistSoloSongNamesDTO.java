package com.alespotify.main.models.dto;

import com.alespotify.main.models.entities.Album;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ArtistSoloSongNamesDTO {
    private String id;
    private String name;
    private String image;
    private String description;
    private List<Album> albums; // cambiar a AlbumDTO solo nombres ???
    private List<Map<String, String>> songs;

}

