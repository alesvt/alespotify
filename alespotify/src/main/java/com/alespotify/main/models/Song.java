package com.alespotify.main.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.*;

import java.util.ArrayList;

@Data
@Document(collection = "songs")
public class Song {
    // @Id
    @MongoId(FieldType.OBJECT_ID)
    private ObjectId id;

    @Field(name = "song_artists")
    @DocumentReference
    private ArrayList<Artist> artists;

    @Field(name = "song_title")
    private String title;
    @Field(name = "song_length")
    private int length;

    @DocumentReference
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

    public Song(int addedToPlaylists, int timesPlayed, ArrayList<String> genres, String thumbImage, String source, int length, String title, ArrayList<Artist> artists) {
        this.id = new ObjectId();
        this.addedToPlaylists = addedToPlaylists;
        this.timesPlayed = timesPlayed;
        this.genres = genres;
        this.thumbImage = thumbImage;
        this.source = source;
        // this.album = album;
        this.length = length;
        this.title = title;
        this.artists = new ArrayList<>();
    }
}

