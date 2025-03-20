package com.alespotify.main.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigInteger;
import java.util.ArrayList;


@Data
@Document(collection = "artists")
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ObjectId id;

    @Field(name = "artist_name")
    private String name;
    @Field(name = "artist_image")
    private String image;
    @Field(name = "artist_description")
    private String description;
    @Field(name = "artist_albums")
    private ArrayList<Album> albums;
    @Field(name = "artist_songs")
    private ArrayList<Song> songs;

    public Artist() {
    }

    public Artist(String name, String image, String description, ArrayList<Album> albums, ArrayList<Song> songs) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.albums = albums;
        this.songs = songs;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(ArrayList<Album> albums) {
        this.albums = albums;
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }
}
