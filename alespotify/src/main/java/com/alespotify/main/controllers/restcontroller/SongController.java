package com.alespotify.main.controllers.restcontroller;


import com.alespotify.main.models.dto.CancionConArtistasReducido;
import com.alespotify.main.models.dto.SongArtistDTO;
import com.alespotify.main.models.dto.SongArtistInfoFromSongDTO;
import com.alespotify.main.models.dto.SongDTO;
import com.alespotify.main.models.entities.Song;
import com.alespotify.main.repository.ArtistRepository;
import com.alespotify.main.repository.SongRepository;
import com.alespotify.main.repository.UserRepository;
import com.alespotify.main.util.SongMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/songs")
public class SongController {


    private final SongRepository songRepository;
    private final UserRepository userRepository;
    private final ArtistRepository artistRepository;

    public SongController(SongRepository songRepository, UserRepository userRepository, ArtistRepository artistRepository) {
        this.songRepository = songRepository;
        this.userRepository = userRepository;
        this.artistRepository = artistRepository;
    }


    @GetMapping
    public ResponseEntity<List<Song>> findAllSongs() {
        System.out.println("saca canciones");
        return ResponseEntity.ok(songRepository.findAll());
//        return songRepository.findAll()
//                .stream()
//                .map(SongMapper::toCancionConArtistasReducido)
//                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public SongDTO findSong(@PathVariable String id) {
        Song song = songRepository.findById(id).orElse(null);
        if (song != null) {
            return SongMapper.toSongDTO(song);
        }
        return null;
    }


    @PostMapping(consumes = "application/json")
    public Song createSong(@RequestBody Song song) {
        return songRepository.save(song);
    }
}
