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
import kotlinx.coroutines.launch

sealed class SongDataState {
    object Loading : SongDataState()
    data class Success(val items: List<Cancion>) : SongDataState()
    data class Error(val mensaje: String) : SongDataState()
}

sealed class ArtistDataState {
    object Loading : ArtistDataState()
    data class Success(val items: List<Artist>) : ArtistDataState()
    data class Error(val mensaje: String) : ArtistDataState()
}


class AppViewModel : ViewModel() {

    private val apiService = ApiService()

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
    private val _playlists = MutableStateFlow<List<Playlist>?>(null)
    val playlists: StateFlow<List<Playlist>?> = _playlists
    private val _isLoadingPlaylists = MutableStateFlow(false)
    val isLoadingPlaylists: StateFlow<Boolean> = _isLoadingPlaylists
    private val _playlistsLoaded = MutableStateFlow(false)
    val playlistsLoaded: StateFlow<Boolean> = _playlistsLoaded

    fun loadSongs() {
        viewModelScope.launch {
            try {

                // Cargar las tres listas
                val fetchedSongs = apiService.getSongs()
                val fetchedArtists = apiService.getArtists()
                val fetchedPlaylists = apiService.getPlaylists()
// FIXME cambiar a stateflow
                _songs.value = fetchedSongs
                // Imprimir resultados para debug
                println("Canciones cargadas: ${fetchedSongs?.size}")
                println("Artistas cargados: ${fetchedArtists?.size}")
                println("Playlists cargadas: ${fetchedPlaylists?.size}")
                if (fetchedSongs != null) {
                    // Actualizar los estados con los resultados
                    _songDataState.value = SongDataState.Success(fetchedSongs)
                }
                if (fetchedArtists != null) {
                    _artistDataState.value = ArtistDataState.Success(fetchedArtists)

                }
                if (fetchedPlaylists != null) {
                    _playlistDataState.value = PlaylistDataState.Success(fetchedPlaylists)
                }


            } catch (e: Exception) {
                // todo mas errores
                errorMessage = "Error al cargar artistas" + e.message
                _songDataState.value = SongDataState.Error("error al cargar datos " + e.message)
            } finally {
                isLoading = false
            }
        }
    }
}