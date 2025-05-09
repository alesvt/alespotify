package com.alespotify.main.models.entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
    @JoinColumn(name = "album_id")
    private Album album;



    @ManyToMany
    @JoinTable(
            name = "cancion_artista",
            joinColumns = @JoinColumn(name="cancion_id"),
            inverseJoinColumns = @JoinColumn(name = "artista_id")
    )
    private Set<Artista> artists = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(
            name = "playlist_cancion",
            joinColumns = @JoinColumn(name = "cancion_id"),
            inverseJoinColumns = @JoinColumn(name = "playlist_id")
    )
    private Set<Playlist> playlists = new LinkedHashSet<>();

}