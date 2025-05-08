package com.alespotify.main.models.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "playlist_cancion")
public class PlaylistCancion implements Serializable {
    @Serial
    private static final long serialVersionUID = 4703413336575450210L;
    @EmbeddedId
    private PlaylistCancionId id;

    @MapsId("playlistId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "playlist_id", nullable = false)
    private Playlist playlist;

    @MapsId("cancionId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "cancion_id", nullable = false)
    private Cancion song;

    @ColumnDefault("'1'")
    @Column(name = "veces_en_playlist", columnDefinition = "int UNSIGNED")
    private Long timesAddedToPlaylist;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "fecha_agregacion")
    private Instant addedAt;

}