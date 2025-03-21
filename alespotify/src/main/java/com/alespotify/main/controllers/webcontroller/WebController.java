package com.alespotify.main.controllers.webcontroller;


import com.alespotify.main.models.Artist;
import com.alespotify.main.models.Song;
import com.alespotify.main.models.User;
import com.alespotify.main.service.ArtistServiceImpl;
import com.alespotify.main.service.SongServiceImpl;
import com.alespotify.main.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class WebController {

    private final UserService userService;
    private final SongServiceImpl songService;
    private final ArtistServiceImpl artistService;


    public WebController(UserService userService, SongServiceImpl songService, ArtistServiceImpl artistService) {
        this.songService = songService;
        this.userService = userService;
        this.artistService = artistService;
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/info")
    public String info(Model model) {
        model.addAttribute("nombre", "ales");
        return "info";
    }

    @GetMapping("/app")
    public String app(Model model, User user) {
        List<Song> songs = songService.getAllSongs();
        List<Artist> artists = artistService.getAllArtists();
        model.addAttribute("user", user);
        model.addAttribute("artists", artists);
        model.addAttribute("songs", songs);
        return "app";
    }
}
