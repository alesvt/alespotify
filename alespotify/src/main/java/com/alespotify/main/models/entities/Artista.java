package com.alespotify.main.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "artista")
public class Artista implements Serializable {
    @Serial
    private static final long serialVersionUID = 2051355511924143684L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false)
    private String name;

    @Lob
    @Column(name = "descripcion")
    private String description;

    @Column(name = "imagen")
    private String image;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "fecha_creacion")
    private Instant creationDate;

    @OneToMany
    private Set<Album> albums = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "artists")
    @JsonIgnore
    @JsonIgnoreProperties({"artists", "album", "playlists", "hibernateLazyInitializer", "handler"})
    private Set<Cancion> songs = new LinkedHashSet<>();

    public Artista() {
    }

    public Artista(Integer id, String name, String description, String image, Instant creationDate, Set<Album> albums, Set<Cancion> songs) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.creationDate = creationDate;
        this.albums = albums;
        this.songs = songs;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Set<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }

    public Set<Cancion> getSongs() {
        return songs;
    }

    public void setSongs(Set<Cancion> songs) {
        this.songs = songs;
    }
}