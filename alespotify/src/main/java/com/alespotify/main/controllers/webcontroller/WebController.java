package com.alespotify.main.controllers.webcontroller;

import com.alespotify.main.models.entities.*;
import com.alespotify.main.repository.*;
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

    private final UserService userService;
    private final SongService songService;
    private final SongRepository songRepository;
    private final ArtistRepository artistaRepository;
    private final PlaylistRepository playlistRepository;
    private final AlbumRepository albumRepository;
    private final FavoritoRepository favoritoRepository;

    public WebController(AlbumRepository albumRepository, SongRepository songRepository, UserService userService,
                         SongService songService, PlaylistRepository playlistRepository, ArtistRepository artistaRepository, FavoritoRepository favoritoRepository) {
        this.userService = userService;
        this.songRepository = songRepository;
        this.songService = songService;
        this.playlistRepository = playlistRepository;
        this.artistaRepository = artistaRepository;
        this.albumRepository = albumRepository;
        this.favoritoRepository = favoritoRepository;
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }


    @GetMapping("/app")
    public String app(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        Optional<Usuario> user = userService.findByEmail(currentUserName);
        if (user.isPresent()) {
            List<Cancion> songs = songRepository.findByVecesReproducido();
            List<Album> albumes = albumRepository.findNewestAlbums();
            List<Artista> artists = artistaRepository.findTop3Artists();
            Favorito favoritos = user.get().getFavoritos();
            List<Playlist> playlists = playlistRepository.findPublicPlaylists();
            List<Playlist> userPlaylists = playlistRepository.findByUserId(Long.valueOf(user.get().getId()));
            model.addAttribute("userPlaylists", userPlaylists);
            model.addAttribute("user", user.get());
            model.addAttribute("artists", artists);
            model.addAttribute("playlists", playlists);
            model.addAttribute("songs", songs);
            model.addAttribute("albums", albumes);
        }

        return "app";
    }
}