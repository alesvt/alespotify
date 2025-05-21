package com.alespotify.main.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;


@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {
    @Serial
    private static final long serialVersionUID = 995285320948017396L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "contrasena", nullable = false)
    private String password;

    @Column(name = "imagen")
    private String imagen;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "fecha_creacion")
    private Instant creationDate;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<Playlist> playlists = new LinkedHashSet<>();

    @JsonIgnore
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Favorito favoritos;

    public Usuario() {
    }

    public Usuario(Integer id, String name, String email, String password, String imagen, Instant creationDate, Set<Playlist> playlists, Favorito favoritos) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.imagen = imagen;
        this.creationDate = creationDate;
        this.playlists = playlists;
        this.favoritos = favoritos;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Set<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(Set<Playlist> playlists) {
        this.playlists = playlists;
    }

    public Favorito getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(Favorito favoritos) {
        this.favoritos = favoritos;
    }
}