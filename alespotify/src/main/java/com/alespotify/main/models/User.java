package com.alespotify.main.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import org.bson.types.ObjectId;

import java.util.ArrayList;


@Document(collection = "users")
@Data
public class User {

    @Id
    private ObjectId id;

    @Field(name = "user_name")
    private String username;
    @Field(name = "user_password")
    private String password;
    @Field(name = "user_email")
    private String email;


    @JsonIgnore
    @DocumentReference
    @Field("user_playlists")
    private ArrayList<Playlist> playlists;

    /**
     * private Favourites favourites;
     */
    @JsonIgnore
    @DocumentReference
    @Field("user_favourites")
    private Playlist favourites;

    public User() {
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.id = new ObjectId();
    }
}
