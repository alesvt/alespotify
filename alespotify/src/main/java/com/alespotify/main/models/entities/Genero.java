package com.alespotify.main.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "genero")
public class Genero implements Serializable {
    @Serial
    private static final long serialVersionUID = 7415058655048759735L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 50)
    private String name;

    @OneToMany(mappedBy = "genre", fetch = FetchType.LAZY)
    @JsonIgnoreProperties(ignoreUnknown = true, value = {"genre"})
    private Set<Cancion> songs = new LinkedHashSet<>();

    public Genero() {
    }

    public Genero(Integer id, String name, Set<Cancion> songs) {
        this.id = id;
        this.name = name;
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

    public Set<Cancion> getSongs() {
        return songs;
    }

    public void setSongs(Set<Cancion> songs) {
        this.songs = songs;
    }
}