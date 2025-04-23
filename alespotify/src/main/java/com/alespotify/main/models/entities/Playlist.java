package com.alespotify.main.models.entities;


import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Data
@Document(collection = "playlists")
public class Playlist {


    @MongoId(FieldType.OBJECT_ID)
    private String id;

    @Field(name = "playlist_name")
    private String name;

    @Field("playlist_image")
    private String image;

    @Field("playlist_creation_date")
    private String creationDate;

    @Field("playlist_update_date")
    private String updateDate;

    @Field("public")
    private boolean publicPlaylist;



    @Field(name = "playlist_songs")
    @DocumentReference
    private List<Song> songs;

    @Field(name = "user")
    @DocumentReference
    @JsonBackReference
    private User user;


    public Playlist() {
    }

}
