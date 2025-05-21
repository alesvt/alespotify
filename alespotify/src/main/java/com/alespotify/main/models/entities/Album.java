package com.alespotify.main.models.entities;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;


@Entity
@Table(name = "album")
public class Album implements Serializable {
    @Serial
    private static final long serialVersionUID = -7539159829410047692L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false)
    private String name;

    @Column(name = "imagen")
    private String image;

    @Column(name = "fecha_salida")
    private LocalDate releaseDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artista_id")
    private Artista artist;

    @OneToMany(mappedBy = "album")
    private Set<Cancion> songs = new LinkedHashSet<>();

    public Album() {
    }

    public Album(Integer id, String name, String image, LocalDate releaseDate, Artista artist, Set<Cancion> songs) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.releaseDate = releaseDate;
        this.artist = artist;
        this.songs = songs;
    }

    public Set<Cancion> getSongs() {
        return songs;
    }

    public void setSongs(Set<Cancion> songs) {
        this.songs = songs;
    }

    public Artista getArtist() {
        return artist;
    }

    public void setArtist(Artista artist) {
        this.artist = artist;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}