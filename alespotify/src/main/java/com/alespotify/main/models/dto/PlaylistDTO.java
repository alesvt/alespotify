package com.alespotify.main.models.dto;

import com.alespotify.main.models.entities.User;
import lombok.Data;

import java.util.List;

@Data
public class PlaylistDTO {
    private String name;
    private String id;
    private String image;
    private User user;
    private List<CancionConArtistasReducido> songs;
    private Boolean publicPlaylist;

}
