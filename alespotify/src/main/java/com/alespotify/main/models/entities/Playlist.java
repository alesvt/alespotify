package com.alespotify.main.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;


@Entity
@Table(name = "playlist")
public class Playlist implements Serializable {
    @Serial
    private static final long serialVersionUID = 5504186715903122670L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario user;

    @ColumnDefault("0")
    @Column(name = "publica")
    private Boolean isPublic;

    @Column(name = "imagen")
    private String image;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "fecha_creacion")
    private Instant creationDate;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "fecha_edicion")
    private Instant updateDate;


    @OneToOne(mappedBy = "playlist")
    @JsonIgnore
    private Favorito favoritos;

    @ManyToMany
    @JoinTable(
            name = "playlist_cancion",
            joinColumns = @JoinColumn(name = "playlist_id"),
            inverseJoinColumns = @JoinColumn(name = "cancion_id")
    )
    private Set<Cancion> songs = new LinkedHashSet<>();


    public Playlist() {
    }

    public Playlist(Integer id, String nombre, Usuario user, Boolean isPublic, String image, Instant creationDate, Instant updateDate, Favorito favoritos, Set<Cancion> songs) {
        this.id = id;
        this.nombre = nombre;
        this.user = user;
        this.isPublic = isPublic;
        this.image = image;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
        this.favoritos = favoritos;
        this.songs = songs;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
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

    public Instant getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }

    public Favorito getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(Favorito favoritos) {
        this.favoritos = favoritos;
    }

    public Set<Cancion> getSongs() {
        return songs;
    }

    public void setSongs(Set<Cancion> songs) {
        this.songs = songs;
    }
}