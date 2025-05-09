package com.alespotify.main.service;

import com.alespotify.main.models.entities.Artista;
import com.alespotify.main.models.entities.Cancion;
import com.alespotify.main.repository.ArtistRepository;
import com.alespotify.main.repository.SongRepository;
import com.alespotify.main.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;

@Service
public class SongService {

    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;

    public SongService(SongRepository songRepository, ArtistRepository artistRepository) {
        this.songRepository = songRepository;
        this.artistRepository = artistRepository;

    }

    @Transactional
    public Cancion crearCancion(Cancion cancion) {
        if(cancion.getArtists() != null && !cancion.getArtists().isEmpty()) {
            Set<Artista> artistas = new LinkedHashSet<>();
            for (Artista artista : cancion.getArtists()) {
                if(artista.getId() != null) {
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
