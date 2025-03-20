package com.alespotify.main.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;

@Document(collection = "songs")
@Data
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ObjectId id;
    @Field(name = "song_artists")
    private ArrayList<Artist> artists;


    @Field(name = "song_title")
    private String title;
    @Field(name = "song_length")
    private int length;
    @Field(name = "song_album")
    private Album album;
    @Field(name = "song_source")
    private String source;
    @Field(name = "song_thumb_image")
    private String thumbImage;
    @Field(name = "song_genre")
    private ArrayList<String> genres;
    @Field(name = "times_played")
    private int timesPlayed;

    @Field(name = "added_to_playlists")
    private int addedToPlaylists;

    public Song() {
    }
    public Song(ObjectId id, String title, int length, ArrayList<Artist> artists, Album album, String source, String thumbImage, ArrayList<String> genres, int timesPlayed, int addedToPlaylists) {

        this.id = id;
        this.title = title;
        this.length = length;
        this.artists = artists;
        this.album = album;
        this.source = source;
        this.thumbImage = thumbImage;
        this.genres = genres;
        this.timesPlayed = timesPlayed;
        this.addedToPlaylists = addedToPlaylists;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public ArrayList<Artist> getArtists() {
        return artists;
    }

    public void setArtists(ArrayList<Artist> artists) {
        this.artists = artists;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getThumbImage() {
        return thumbImage;
    }

    public void setThumbImage(String thumbImage) {
        this.thumbImage = thumbImage;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public int getTimesPlayed() {
        return timesPlayed;
    }

    public void setTimesPlayed(int timesPlayed) {
        this.timesPlayed = timesPlayed;
    }

    public int getAddedToPlaylists() {
        return addedToPlaylists;
    }

    public void setAddedToPlaylists(int addedToPlaylists) {
        this.addedToPlaylists = addedToPlaylists;
    }
}

