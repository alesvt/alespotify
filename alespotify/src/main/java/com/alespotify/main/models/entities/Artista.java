package com.alespotify.main.models.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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

    @OneToMany(mappedBy = "artista")
    private Set<Album> albums = new LinkedHashSet<>();

    @ManyToMany
    private Set<Cancion> songs = new LinkedHashSet<>();

}