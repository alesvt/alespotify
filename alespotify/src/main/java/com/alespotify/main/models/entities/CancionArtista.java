package com.alespotify.main.models.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serial;
import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cancion_artista")
public class CancionArtista implements Serializable {
    @Serial
    private static final long serialVersionUID = -1030201839110937093L;
    @EmbeddedId
    private CancionArtistaId id;

    @MapsId("cancionId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "cancion_id", nullable = false)
    private Cancion song;

    @MapsId("artistaId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "artista_id", nullable = false)
    private Artista artist;

}