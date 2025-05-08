package com.alespotify.main.models.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "usuario_id")
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



  //  @OneToMany(mappedBy = "playlist")
 //   private Set<PlaylistCancion> playlistSongs = new LinkedHashSet<>();

}