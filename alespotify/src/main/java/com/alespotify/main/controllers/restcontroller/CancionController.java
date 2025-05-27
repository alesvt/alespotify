package com.alespotify.main.controllers.restcontroller;

import ch.qos.logback.core.model.Model;
import com.alespotify.main.models.entities.Cancion;
import com.alespotify.main.models.entities.Favorito;
import com.alespotify.main.models.entities.Usuario;
import com.alespotify.main.repository.SongRepository;
import com.alespotify.main.repository.UserRepository;
import com.alespotify.main.service.SongService;
import com.alespotify.main.service.UserService;
import org.apache.coyote.Response;
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

    public CancionController(SongRepository songRepository, SongService songService) {
        this.songRepository = songRepository;
        this.songService = songService;
    }

    @GetMapping
    public List<Cancion> listarCanciones() {
        return songRepository.findByVecesReproducido();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cancion> buscarCancion(@PathVariable Long id) {
        Optional<Cancion> cancion = songRepository.findById(id);
        if (cancion.isPresent()) {
            Cancion c = cancion.get();
            c.setTimesPlayed(c.getTimesPlayed() + 1);
            songRepository.save(c);
            return ResponseEntity.ok(c);
        }
        return null;
    }

    @PostMapping(value = "/new",
            consumes = "application/json")
    public ResponseEntity<Cancion> crearCancion(@RequestBody Cancion cancion) {
        Cancion nuevaCancion = songService.crearCancion(cancion);
        Cancion cancionConRelaciones = songRepository.findById(Long.valueOf(nuevaCancion.getId()))
                .orElseThrow(() -> new RuntimeException("Error"));
        return ResponseEntity.ok(cancionConRelaciones);
    }

    @GetMapping("/song")
    public Cancion playSong(@RequestParam Long id) {
        Optional<Cancion> cancion = songRepository.findById(id);
        if (cancion.isPresent()) {
            Cancion c = cancion.get();
            c.setTimesPlayed(c.getTimesPlayed() + 1);
            songRepository.save(c);
            return c;
        }
        return null;
    }

    @GetMapping("/song/name")
    public List<Cancion> getSongSearch(@RequestParam String nombre) {
        Optional<List<Cancion>> canciones = songRepository.findByNombre(nombre);
        return canciones.orElse(null);
    }
}
