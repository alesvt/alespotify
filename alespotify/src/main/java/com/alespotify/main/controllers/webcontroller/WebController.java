package com.alespotify.main.controllers.webcontroller;

import com.alespotify.main.models.entities.Artista;
import com.alespotify.main.models.entities.Cancion;
import com.alespotify.main.models.entities.Playlist;
import com.alespotify.main.models.entities.Usuario;
import com.alespotify.main.repository.ArtistRepository;
import com.alespotify.main.repository.PlaylistRepository;
import com.alespotify.main.repository.SongRepository;
import com.alespotify.main.repository.UserRepository;
import com.alespotify.main.service.SongService;
import com.alespotify.main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class WebController {

    @Autowired
    private final UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SongService songService;
    @Autowired
    private SongRepository songRepository;
    private final ArtistRepository artistaRepository;
    @Autowired
    private PlaylistRepository playlistRepository;


    public WebController(UserService userService,
            /*, SongServiceImpl songService, ArtistServiceImpl artistService, PlaylistRepository playlistRepository, PlaylistServiceImpl playlistService
             */
                         ArtistRepository artistaRepository) {
        this.userService = userService;
       /* this.songService = songService;
        this.artistService = artistService;
        this.playlistService = playlistService;
        this.playlistRepository = playlistRepository;*/
        this.artistaRepository = artistaRepository;
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
        Optional<Usuario> user = userRepository.findByEmail(currentUserName); // Busca el usuario completo en la base de datos
        if (user.isPresent()) {
            // todo cambiar para sacar solo unas cuantas
            List<Cancion> songs = songRepository.findAll();
            List<Artista> artists = artistaRepository.findAll();
            List<Playlist> playlists = playlistRepository.findAll();

            
            model.addAttribute("user", user.get());
            model.addAttribute("artists", artists);
            model.addAttribute("playlists", playlists);
            model.addAttribute("songs", songs);
        }

        return "app";
    }
}