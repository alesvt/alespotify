package com.alespotify.main.controllers.restcontroller;


import com.alespotify.main.models.Artist;
import com.alespotify.main.models.Song;
import com.alespotify.main.repository.SongRepository;
import com.alespotify.main.service.ISongService;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/songs")
public class SongController {

    Song c1 = new Song();
    Artist a1 = new Artist();
    File file = new File(getClass().getResource("/songs").getFile());
    File[] files = file.listFiles();

    private final SongRepository songRepository;

    public SongController(SongRepository songRepository) {
        this.songRepository = songRepository;
    }


    @GetMapping
    public List<Song> findAllSongs() {
        // artista
        a1.setName("QVDO");
        a1.setId(new ObjectId());
        a1.setDescription("descripcion del artista");
        a1.setImage("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS08JbeYZ8ccPOp4Su1QuQ6xJueP1D-0XFYgA&s");
        // canciones ?? bucle infinito - NO QUIERO

        // cancion
        c1.setTitle("Siete");
        c1.setThumbImage("https://www.orquestafilarmonia.com/wp-content/uploads/2023/09/estilo-musical.png");
        c1.setId(new ObjectId());
        c1.setArtists(new ArrayList<>(List.of(a1)));
        c1.setSource(files[0].getAbsolutePath());
        c1.setGenres(new ArrayList<>(List.of("Reggaeton")));

        return (Arrays.asList(c1, c1));
    }
}
