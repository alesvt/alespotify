package com.alespotify.ui.navigation

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alespotify.model.Artist
import com.alespotify.model.Cancion
import com.alespotify.model.Playlist
import com.alespotify.model.User
import com.alespotify.shared.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class AppViewModel : ViewModel() {

    private val apiService = ApiService()

    private val _songs = MutableStateFlow<List<Cancion>?>(null)
    val songs = _songs.asStateFlow()
    private val _isLoadingSongs = MutableStateFlow(false)
    val isLoadingSongs: StateFlow<Boolean> = _isLoadingSongs
    private val _songsLoaded = MutableStateFlow(false)
    val songsLoaded: StateFlow<Boolean> = _songsLoaded

    // Estados para Artistas
    private val _artists = MutableStateFlow<List<Artist>?>(null)
    val artists: StateFlow<List<Artist>?> = _artists
    private val _isLoadingArtists = MutableStateFlow(false)
    val isLoadingArtists: StateFlow<Boolean> = _isLoadingArtists
    private val _artistsLoaded = MutableStateFlow(false)
    val artistsLoaded: StateFlow<Boolean> = _artistsLoaded

    // playlists
    private val _playlists = MutableStateFlow<List<Playlist>?>(null)
    val playlists: StateFlow<List<Playlist>?> = _playlists
    private val _isLoadingPlaylists = MutableStateFlow(false)
    val isLoadingPlaylists: StateFlow<Boolean> = _isLoadingPlaylists
    private val _playlistsLoaded = MutableStateFlow(false)
    val playlistsLoaded: StateFlow<Boolean> = _playlistsLoaded
    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage


    private val _currentSong = MutableStateFlow<Cancion?>(null)
    val currentSong: StateFlow<Cancion?> = _currentSong

    fun loadSongs() {
        viewModelScope.launch {
            try {
                println("HE ENTRADO")
                // Cargar las tres listas
                val fetchedSongs = apiService.getSongs()
                val fetchedArtists = apiService.getArtists()
                val fetchedPlaylists = apiService.getPlaylists()

                // Imprimir resultados para debug
                println("Canciones cargadas: ${fetchedSongs?.size}")
                println("Artistas cargados: ${fetchedArtists?.size}")
                println("Playlists cargadas: ${fetchedPlaylists?.size}")

                if (fetchedSongs != null) {
                    // Actualizar los estados con los resultados
                    _songs.value = fetchedSongs
                    _songsLoaded.value = true
                    _songs.emit(fetchedSongs)
                }
                if (fetchedArtists != null) {
                    _artists.value = fetchedArtists
                    _artistsLoaded.value = true
                    _artists.emit(fetchedArtists)
                }
                if (fetchedPlaylists != null) {
                    _playlists.value = fetchedPlaylists
                    _playlistsLoaded.value = true
                    _playlists.emit(fetchedPlaylists)
                }


            } catch (e: Exception) {
                // todo mas errores
                _errorMessage.value = "Error al cargar valores: " + e.cause

            } finally {

            }
        }
    }
}