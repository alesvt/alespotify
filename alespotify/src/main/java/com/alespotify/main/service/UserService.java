package com.alespotify.main.service;

import com.alespotify.main.models.entities.Favorito;
import com.alespotify.main.models.entities.Playlist;
import com.alespotify.main.models.entities.Usuario;
import com.alespotify.main.repository.FavoritoRepository;
import com.alespotify.main.repository.PlaylistRepository;
import com.alespotify.main.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {


    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final PlaylistRepository playlistRepository;
    @Autowired
    private final FavoritoRepository favoritoRepository;


    @Transactional
    public Usuario registerUser(Usuario user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);
        user.setCreationDate(Instant.now());
        System.out.println(user.getPassword() + ", " + user.getName() + ", " + user.getId());

        System.out.println(user.getId());
        // System.out.println(user);

        Playlist playlist = crearPlaylist(user);
        playlist = playlistRepository.save(playlist);

        Favorito favorito = new Favorito();
        favorito.setUser(user);
        favorito.setPlaylist(playlist);
        favorito = favoritoRepository.save(favorito);

        user.setFavoritos(favorito);
        System.out.println(user);
        return user;
    }

    public Playlist crearPlaylist(Usuario user) {
        Playlist playlist = new Playlist();
        playlist.setNombre("Favoritos de " + user.getName());
        playlist.setImage("https://placehold.co/200?text=Mis%20favoritos");
        playlist.setIsPublic(false);
        playlist.setCreationDate(Instant.now());
        playlist.setUpdateDate(playlist.getCreationDate());
        playlist.setUser(user);
        return playlist;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Usuario> user = userRepository.findByEmail(email);
        System.out.println(user);
        if (user.isPresent()) {
            return org.springframework.security.core.userdetails.User
                    .withUsername(user.get().getEmail())
                    .password(user.get().getPassword())
                    .authorities(Collections.emptyList())
                    .build();
        } else {
            throw new UsernameNotFoundException("User not found with email : " + email);
        }
    }

    public Optional<Usuario> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Usuario login(String credentials) {
        JsonParser pars = JsonParserFactory.getJsonParser();
        Map<String, Object> m = pars.parseMap(credentials);
        String email = (String) m.get("email");
        String password = (String) m.get("password");
        Optional<Usuario> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            if (passwordEncoder.matches(password, user.get().getPassword())) {
                return user.get();
            } else {
                return null;
            }
        }
        return null;
    }

}
