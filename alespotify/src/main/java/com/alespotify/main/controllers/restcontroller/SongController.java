package com.alespotify.main.controllers.restcontroller;


import com.alespotify.main.models.Artist;
import com.alespotify.main.models.Song;
import com.alespotify.main.models.User;
import com.alespotify.main.repository.ArtistRepository;
import com.alespotify.main.repository.SongRepository;
import com.alespotify.main.repository.UserRepository;
import org.springframework.web.bind.annotation.*;


import java.util.List;

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
    public List<Song> findAllSongs() {
        return songRepository.findAll();
    }

    @GetMapping("/{id}")
    public Song findSong(@PathVariable String id) {
        return songRepository.findById(id).orElse(null);
    }

    @PostMapping(consumes = "application/json")
    public Song createSong(@RequestBody Song song) {

        return songRepository.save(song);
    }
}
