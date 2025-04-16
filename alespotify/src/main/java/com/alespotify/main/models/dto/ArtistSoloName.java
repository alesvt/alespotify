package com.alespotify.main.models.dto;

import lombok.Data;

@Data
public class ArtistSoloName {
    private String id;
    private String name;

    public ArtistSoloName(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
