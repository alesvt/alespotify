package com.alespotify.main.models.dto;

import com.alespotify.main.models.entities.Album;
import com.alespotify.main.models.entities.Artist;
import lombok.Data;

import java.util.ArrayList;


@Data
public class SongDTO {
    private String id;
    private String title;
    private String thumbImage;
    private String source;
    private int addedToPlaylists;
    private int timesPlayed;
    private ArrayList<String> genres;
    private int length;
    private ArrayList<Artist> artists;
    private Album album;
}

