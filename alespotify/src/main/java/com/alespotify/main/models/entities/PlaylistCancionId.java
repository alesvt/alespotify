package com.alespotify.main.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class PlaylistCancionId implements Serializable {
    private static final long serialVersionUID = -5880226085042247720L;
    @Column(name = "playlist_id", nullable = false)
    private Integer playlistId;

    @Column(name = "cancion_id", nullable = false)
    private Integer songId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PlaylistCancionId entity = (PlaylistCancionId) o;
        return Objects.equals(this.playlistId, entity.playlistId) &&
                Objects.equals(this.songId, entity.songId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playlistId, songId);
    }

}