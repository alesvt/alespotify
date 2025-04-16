package com.alespotify.main.models.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.Map;

@Data
public class SongSoloArtistNamesDTO {
    private String id;
    private String title;
    private String thumbImage;
    private String source;
    private int addedToPlaylists;
    private int timesPlayed;
    private ArrayList<String> genres;
    private int length;
    private ArrayList<ArtistSoloName> artists; // lista de mapas (id, nombre artista)
    private String nombreAlbum;
}


