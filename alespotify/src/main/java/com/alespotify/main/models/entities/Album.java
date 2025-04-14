package com.alespotify.main.models.entities;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.*;

import java.util.ArrayList;
import java.util.Date;

@Document(collection = "albums")
@Data
public class Album {
    @MongoId(FieldType.OBJECT_ID)
    private ObjectId id;
    @Field(name = "album_name")
    private String name;
    @Field(name = "album_image")
    private String image;

    @DocumentReference
    @Field(name = "album_songs")
    private ArrayList<Song> songs;


    @DocumentReference
    @Field(name = "album_artist")
    private Artist artist;
    @Field(name = "album_release_date")
    private Date fecha;

    public Album() {
    }

    public Album(String name, String image, ArrayList<Song> songs, Artist artist, Date fecha) {
        this.name = name;
        this.image = image;
        this.songs = songs;
        this.artist = artist;
        this.fecha = fecha;
        this.id = new ObjectId();
    }
}