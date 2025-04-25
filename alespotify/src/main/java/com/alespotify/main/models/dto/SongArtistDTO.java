package com.alespotify.main.models.dto;

import lombok.Data;

import java.util.List;

@Data
public class SongArtistDTO {
    private String id;
    private String description;
    private String name;
    private String image;
    private List<SongArtistInfoFromSongDTO> songs;

}

