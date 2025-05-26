package com.alespotify.ui.navigation

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alespotify.model.Cancion
import com.alespotify.model.Playlist
import com.alespotify.model.player.MediaPlayer
import com.alespotify.model.player.createMediaPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class QueueViewModel : ViewModel() {

    private val _currentSong = MutableStateFlow<Cancion?>(null)
    val currentSong: StateFlow<Cancion?> = _currentSong

    private val _songCalled = MutableStateFlow<Cancion?>(null)
    val songCalled = _songCalled.asStateFlow()

    private val _currentPlaylist = MutableStateFlow<Playlist?>(null)
    var currentPlaylist = _currentPlaylist.asStateFlow()

    val mediaPlayer: MediaPlayer = createMediaPlayer()

    val isPlaying: StateFlow<Boolean> = mediaPlayer.isPlaying
    val currentPosition: StateFlow<Long> = mediaPlayer.currentPosition
    val duration: StateFlow<Long> = mediaPlayer.duration

    private val _volume = MutableStateFlow(0.8f)
    val volume: StateFlow<Float> = _volume.asStateFlow()

    private val _queue = MutableStateFlow<List<Cancion>>(emptyList())
    val queue: StateFlow<List<Cancion>> = _queue.asStateFlow()

    private val _currentIndex = MutableStateFlow(0)
    val currentIndex: StateFlow<Int> = _currentIndex.asStateFlow()

    fun playSong(song: Cancion) {
        viewModelScope.launch {
            try {
                mediaPlayer.playSong(song)
                _currentSong.value = song
                _songCalled.value = song
            } catch (e: Exception) {
                // Handle error
                println("Error playing song: ${e.message}")
            }
        }
    }

    fun playlistClick(playlist: Playlist, startIndex: Int = 0) {
        viewModelScope.launch {
            _currentPlaylist.value = currentPlaylist.value?.copy()
            mediaPlayer.playPlaylist(playlist, startIndex)
        }
    }

    fun playPause() {
        viewModelScope.launch {
            if (isPlaying.value) {
                mediaPlayer.pause()
            } else {
                mediaPlayer.play()
            }
        }
    }

    fun stop() {
        viewModelScope.launch {
            mediaPlayer.stop()
        }
    }

    fun seekTo(position: Long) {
        viewModelScope.launch {
            mediaPlayer.seekTo(position)
        }
    }

    fun setVolume(volume: Float) {
        viewModelScope.launch {
            val clampedVolume = volume.coerceIn(0f, 1f)
            _volume.value = clampedVolume
            mediaPlayer.setVolume(clampedVolume)
        }
    }

    fun setQueue(songs: List<Cancion>, startIndex: Int = 0) {
        _queue.value = songs
        _currentIndex.value = startIndex
        if (songs.isNotEmpty() && startIndex < songs.size) {
            playSong(songs[startIndex])
        }
    }

    fun playNext() {
        val queue = _queue.value
        val currentIndex = _currentIndex.value
        if (queue.isNotEmpty() && currentIndex < queue.size - 1) {
            val nextIndex = currentIndex + 1
            _currentIndex.value = nextIndex
            playSong(queue[nextIndex])
        }
    }

    fun playPrevious() {
        val queue = _queue.value
        val currentIndex = _currentIndex.value
        if (queue.isNotEmpty() && currentIndex > 0) {
            val previousIndex = currentIndex - 1
            _currentIndex.value = previousIndex
            playSong(queue[previousIndex])
        }
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayer.release()
    }
}