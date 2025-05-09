package com.alespotify.main.controllers.restcontroller;

import ch.qos.logback.core.model.Model;
import com.alespotify.main.models.entities.Cancion;
import com.alespotify.main.models.entities.Favorito;
import com.alespotify.main.models.entities.Usuario;
import com.alespotify.main.repository.SongRepository;
import com.alespotify.main.repository.UserRepository;
import com.alespotify.main.service.SongService;
import com.alespotify.main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/songs")
public class CancionController {

    private final SongRepository songRepository;
    private final SongService songService;

    public CancionController(UserRepository userRepository, SongRepository songRepository, SongService songService) {
        this.songRepository = songRepository;
        this.songService = songService;
    }

    @GetMapping
    public List<Cancion> listarCanciones() {
        System.out.println(songRepository.findAll());
        return songRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cancion> buscarCancion(@PathVariable Long id) {
        Optional<Cancion> cancion = songRepository.findById(id);
        return cancion.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/new",
            consumes = "application/json")
    public ResponseEntity<Cancion> crearCancion(@RequestBody Cancion cancion) {
        Cancion nuevaCancion = songService.crearCancion(cancion);
        Cancion cancionConRelaciones = songRepository.findById(Long.valueOf(nuevaCancion.getId()))
                .orElseThrow(() -> new RuntimeException("Error"));
        return ResponseEntity.ok(cancionConRelaciones);
    }

}
