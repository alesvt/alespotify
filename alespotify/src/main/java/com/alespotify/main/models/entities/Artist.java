package com.alespotify.main.models.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.*;

import java.util.ArrayList;


@Document(collection = "artists")
@Data
public class Artist {

    // @Id
    @MongoId(FieldType.OBJECT_ID)

    private String id;

    @Field(name = "artist_name")
    private String name;
    @Field(name = "artist_image")
    private String image;
    @Field(name = "artist_description")
    private String description;

    @DocumentReference
    @Field(name = "artist_albums")
    private ArrayList<Album> albums;

    @DocumentReference
    @Field(name = "artist_songs")
    private ArrayList<Song> songs;


    public Artist() {
    }

    public Artist(String id, String name, String image, String description, ArrayList<Song> songs, ArrayList<Album> albums, ArrayList<Artist> artists) {
        //this.id = new ObjectId();
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;
        this.albums = albums;
        this.songs = songs;
    }

}
