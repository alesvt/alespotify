package com.alespotify.main.models.entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;


@Entity
@Table(name = "cancion")
public class Cancion implements Serializable {
    @Serial
    private static final long serialVersionUID = 625815496176823995L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false)
    private String name;

    @Column(name = "enlace", nullable = false)
    private String source;

    @Column(name = "imagen")
    private String image;

    @Column(name = "duracion", columnDefinition = "int UNSIGNED")
    private Long duration;

    @ColumnDefault("'0'")
    @Column(name = "veces_reproducido", columnDefinition = "int UNSIGNED")
    private Long timesPlayed;

    @ManyToOne
    @JoinColumn(name = "genero_id")
    @JsonIgnoreProperties("songs")
    private Genero genre;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "album_id")
    private Album album;


    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnoreProperties({"canciones", "albums", "hibernateLazyInitializer", "handler"})
    @JoinTable(
            name = "cancion_artista",
            joinColumns = @JoinColumn(name = "cancion_id"),
            inverseJoinColumns = @JoinColumn(name = "artista_id")
    )
    private Set<Artista> artists = new LinkedHashSet<>();

    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "playlist_cancion",
            joinColumns = @JoinColumn(name = "cancion_id"),
            inverseJoinColumns = @JoinColumn(name = "playlist_id")
    )
    private Set<Playlist> playlists = new LinkedHashSet<>();

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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getTimesPlayed() {
        return timesPlayed;
    }

    public void setTimesPlayed(Long timesPlayed) {
        this.timesPlayed = timesPlayed;
    }

    public Genero getGenre() {
        return genre;
    }

    public void setGenre(Genero genre) {
        this.genre = genre;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Set<Artista> getArtists() {
        return artists;
    }

    public void setArtists(Set<Artista> artists) {
        this.artists = artists;
    }

    public Set<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(Set<Playlist> playlists) {
        this.playlists = playlists;
    }

    public Cancion() {
    }

    public Cancion(Integer id, String name, String source, String image, Long duration, Long timesPlayed, Genero genre, Album album, Set<Artista> artists, Set<Playlist> playlists) {
        this.id = id;
        this.name = name;
        this.source = source;
        this.image = image;
        this.duration = duration;
        this.timesPlayed = timesPlayed;
        this.genre = genre;
        this.album = album;
        this.artists = artists;
        this.playlists = playlists;
    }
}