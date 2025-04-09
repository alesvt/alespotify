package com.alespotify.main.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.*;

import java.util.ArrayList;
import java.util.Date;

@Document
@Data
public class Playlist {


    @MongoId(FieldType.OBJECT_ID)
    private ObjectId id;

    @Field(name = "playlist_name")
    private String name;

    @Field("playlist_image")
    private String image;

    @Field("playlist_creation_date")
    private Date creationDate;

    @Field("playlist_update_date")
    private Date updateDate;

    @Field("public")
    private boolean publicPlaylist;

    @DocumentReference
    @Field(name = "playlist_songs")
    private ArrayList<Song> songs;


    @Field(name = "playlist_user")
    @DocumentReference
    private User user;

    public Playlist() {
    }

    public Playlist(String name, String image, Date creationDate, ArrayList<Song> songs, User user) {
        this.name = name;
        this.image = image;
        this.creationDate = creationDate;
        this.songs = songs;
        this.user = user;

    }

}
