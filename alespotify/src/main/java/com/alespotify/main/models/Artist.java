package com.alespotify.main.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.*;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import java.math.BigInteger;
import java.util.ArrayList;


@Document(collection = "artists")
@Data
public class Artist {

    // @Id
    @MongoId(FieldType.OBJECT_ID)

    private ObjectId id;

    @Field(name = "artist_name")
    private String name;
    @Field(name = "artist_image")
    private String image;
    @Field(name = "artist_description")
    private String description;

    @DocumentReference
    @Field(name = "artist_albums")
    private ArrayList<Album> albums;

    @JsonIgnore
    @DocumentReference
    @Field(name = "artist_songs")
    private ArrayList<Song> songs;


    public Artist() {
    }

    public Artist(String name, String image, String description, ArrayList<Song> songs) {
        this.id = new ObjectId();
        this.name = name;
        this.image = image;
        this.description = description;
        // this.albums = albums;
        this.songs = songs;
    }

}
