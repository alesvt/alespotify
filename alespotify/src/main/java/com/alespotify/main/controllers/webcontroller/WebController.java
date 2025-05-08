package com.alespotify.main.controllers.webcontroller;

import com.alespotify.main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @Autowired
    private final UserService userService;


    public WebController(UserService userService
            /*, SongServiceImpl songService, ArtistServiceImpl artistService, PlaylistRepository playlistRepository, PlaylistServiceImpl playlistService
             */) {
        this.userService = userService;
       /* this.songService = songService;
        this.artistService = artistService;
        this.playlistService = playlistService;
        this.playlistRepository = playlistRepository;*/
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

/*
    @GetMapping("/app")
    public String app(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName(); // Obtiene el email del usuario
        User user = userService.findByEmail(currentUserName); // Busca el usuario completo en la base de datos

        // todo cambiar para sacar solo unas cuantas
        List<CancionConArtistasReducido> songs = songService.getAllSongsArtistLessInfo();
        List<Artist> artists = artistService.getAllArtists();
        List<PlaylistDTO> playlists = playlistService.getAllPlaylistsDTO();

        model.addAttribute("user", user);
        model.addAttribute("artists", artists);
        model.addAttribute("playlists", playlists);
        model.addAttribute("songs", songs);
        return "app";
    }*/
}