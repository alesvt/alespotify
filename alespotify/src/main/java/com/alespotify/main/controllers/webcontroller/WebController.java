package com.alespotify.main.controllers.webcontroller;


import com.alespotify.main.models.dto.CancionConArtistasReducido;
import com.alespotify.main.models.dto.PlaylistDTO;
import com.alespotify.main.models.dto.SongArtistDTO;
import com.alespotify.main.models.entities.Artist;
import com.alespotify.main.models.entities.Playlist;
import com.alespotify.main.models.entities.Song;
import com.alespotify.main.models.entities.User;
import com.alespotify.main.repository.PlaylistRepository;
import com.alespotify.main.service.ArtistServiceImpl;
import com.alespotify.main.service.PlaylistServiceImpl;
import com.alespotify.main.service.SongServiceImpl;
import com.alespotify.main.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class WebController {

    private final UserService userService;
    private final PlaylistServiceImpl playlistService;
    private final SongServiceImpl songService;
    private final ArtistServiceImpl artistService;
    private final PlaylistRepository playlistRepository;


    public WebController(UserService userService, SongServiceImpl songService, ArtistServiceImpl artistService, PlaylistRepository playlistRepository, PlaylistServiceImpl playlistService) {
        this.songService = songService;
        this.userService = userService;
        this.artistService = artistService;
        this.playlistService = playlistService;
        this.playlistRepository = playlistRepository;
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
    }
}
