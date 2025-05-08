package com.alespotify.main.models.entities;

import jakarta.persistence.*;
import lombok.*;

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

    @OneToMany(mappedBy = "genero")
    private Set<Cancion> songs = new LinkedHashSet<>();

}