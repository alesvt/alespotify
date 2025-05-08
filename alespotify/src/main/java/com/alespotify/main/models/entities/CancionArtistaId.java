package com.alespotify.main.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import org.hibernate.Hibernate;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class CancionArtistaId implements Serializable {
    @Serial
    private static final long serialVersionUID = -4925898870367014775L;
    @Column(name = "cancion_id", nullable = false)
    private Integer songId;

    @Column(name = "artista_id", nullable = false)
    private Integer artistId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CancionArtistaId entity = (CancionArtistaId) o;
        return Objects.equals(this.songId, entity.songId) &&
                Objects.equals(this.artistId, entity.artistId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(songId, artistId);
    }

}