package com.alespotify.main.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @OneToMany(mappedBy = "genre", fetch = FetchType.LAZY)
    @JsonIgnoreProperties(ignoreUnknown = true, value = {"genre"})
    private Set<Cancion> songs = new LinkedHashSet<>();

}