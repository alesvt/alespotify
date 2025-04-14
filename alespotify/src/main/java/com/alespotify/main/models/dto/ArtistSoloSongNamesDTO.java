package com.alespotify.main.models.dto;

import com.alespotify.main.models.entities.Album;
import lombok.Data;

import java.util.ArrayList;
import java.util.Map;

@Data
public class ArtistSoloSongNamesDTO {
    private String id;
    private String name;
    private String image;
    private String description;
    private ArrayList<Album> albums; // cambiar a AlbumDTO solo nombres ???
    private ArrayList<Map<String, String>> songs;

}

