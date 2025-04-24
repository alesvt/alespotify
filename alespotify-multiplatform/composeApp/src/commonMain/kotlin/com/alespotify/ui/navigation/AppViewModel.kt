package com.alespotify.ui.navigation

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
import kotlinx.coroutines.launch

class AppViewModel : ViewModel() {

    private val apiService = ApiService()

    // Estados para Canciones
    private val _songs = MutableStateFlow<List<Cancion>?>(null)
    val songs: StateFlow<List<Cancion>?> = _songs
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

    // Estados para Playlists
    private val _playlists = MutableStateFlow<List<Playlist>?>(null)
    val playlists: StateFlow<List<Playlist>?> = _playlists
    private val _isLoadingPlaylists = MutableStateFlow(false)
    val isLoadingPlaylists: StateFlow<Boolean> = _isLoadingPlaylists
    private val _playlistsLoaded = MutableStateFlow(false)
    val playlistsLoaded: StateFlow<Boolean> = _playlistsLoaded

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage


    init {
        loadSongs()
    }

    private fun loadSongs() {
        viewModelScope.launch {
            _isLoadingSongs.value = true
            try {
               // val fetchedSongs = apiService.getSongs()
                val fetchedArtists = apiService.getArtists()
                // val fetchedPlaylist = apiService.getPlaylists()
               // println(fetchedSongs)
                println(fetchedArtists)
                // println(fetchedPlaylist)
               // _songs.value = fetchedSongs
                _artists.value = fetchedArtists
                // _playlists.value = fetchedPlaylist

                _songsLoaded.value = true
                _artistsLoaded.value = true
                _playlistsLoaded.value = true
            } catch (e: Exception) {
                _errorMessage.value = "Error al cargar canciones: ${e.message}"
            } finally {
                _isLoadingSongs.value = false
            }
        }
    }

}