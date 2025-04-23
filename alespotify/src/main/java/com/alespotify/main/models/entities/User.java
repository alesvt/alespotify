package com.alespotify.main.models.entities;


import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.*;
import lombok.Data;

import org.bson.types.ObjectId;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data
public class User {

    @MongoId(FieldType.OBJECT_ID)
    private String id;

    @Field(name = "user_name")
    private String name;

    @JsonIgnore
    @Field(name = "user_password")
    private String password;
    @Field(name = "user_email")
    private String email;

    @Field(name = "user_image")
    private String image;


    @DocumentReference
    @Field("playlists")
    @JsonManagedReference
    private List<Playlist> playlists;

    @DocumentReference
    @JsonManagedReference
    @Field("favourites")
    private Playlist favourites;

    public User() {
    }

    public User(String id, String name, String password, String email, String image, ArrayList<Playlist> playlists) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.image = image;
        this.playlists = playlists;

    }
}
