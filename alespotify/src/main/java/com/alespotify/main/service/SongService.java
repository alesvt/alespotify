package com.alespotify.main.service;

import com.alespotify.main.models.entities.Artista;
import com.alespotify.main.models.entities.Cancion;
import com.alespotify.main.repository.ArtistRepository;
import com.alespotify.main.repository.SongRepository;
import com.alespotify.main.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@Service
public class SongService {

    @Autowired
    private final SongRepository songRepository;
    @Autowired
    private final ArtistRepository artistRepository;


    @Transactional
    public Cancion crearCancion(Cancion cancion) {
        if (cancion.getArtists() != null && !cancion.getArtists().isEmpty()) {
            Set<Artista> artistas = new LinkedHashSet<>();
            for (Artista artista : cancion.getArtists()) {
                if (artista.getId() != null) {
                    artistRepository.findById(artista.getId())
                            .ifPresent(artistas::add);
                } else {
                    artistas.add(artista);
                }
            }
            cancion.setArtists(artistas);
        }
        return songRepository.save(cancion);
    }


}
