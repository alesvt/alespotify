package com.alespotify.ui.navigation

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

    private val _isShuffleEnabled = MutableStateFlow(false)
    val isShuffleEnabled: StateFlow<Boolean> = _isShuffleEnabled.asStateFlow()

    private val _repeatMode = MutableStateFlow(RepeatMode.OFF)
    val repeatMode: StateFlow<RepeatMode> = _repeatMode.asStateFlow()

    private val _originalQueue = MutableStateFlow<List<Cancion>>(emptyList())

    init {
        viewModelScope.launch {
            mediaPlayer.currentSong.collect { song ->
                _currentSong.value = song
            }
        }
    }

    fun playSong(song: Cancion) {
        viewModelScope.launch {
            println(mediaPlayer.toString())
            try {
                mediaPlayer.playSong(song)
                _songCalled.value = song
                if (_queue.value.isEmpty()) {
                    setQueue(listOf(song), 0)
                }
            } catch (e: Exception) {
                println("Error playing song: ${e.message}")
            }
        }
    }

    fun playPlaylist(playlist: Playlist, startIndex: Int = 0) {
        viewModelScope.launch {
            try {
                _currentPlaylist.value = playlist
                playlist.songs?.let { songs ->
                    setQueue(songs, startIndex)
                    mediaPlayer.playPlaylist(playlist, startIndex)
                }
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }

    fun playPause() {
        viewModelScope.launch {
            try {
                if (isPlaying.value) {
                    mediaPlayer.pause()
                } else {
                    mediaPlayer.play()
                }
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }

    fun seekTo(position: Long) {
        viewModelScope.launch {
            try {
                mediaPlayer.seekTo(position)
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }

    fun setVolume(volume: Float) {
        viewModelScope.launch {
            try {
                val clampedVolume = volume.coerceIn(0f, 1f)
                _volume.value = clampedVolume
                mediaPlayer.setVolume(clampedVolume)
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }

    fun setQueue(songs: List<Cancion>, startIndex: Int = 0) {
        _originalQueue.value = songs
        _queue.value = if (_isShuffleEnabled.value) {
            shuffleQueueKeepingCurrent(songs, startIndex)
        } else {
            songs
        }
        _currentIndex.value =
            if (_isShuffleEnabled.value) 0 else startIndex.coerceIn(0, songs.size - 1)
    }

    fun playNext() {
        viewModelScope.launch {
            try {
                val queue = _queue.value
                val currentIndex = _currentIndex.value

                when {
                    queue.isEmpty() -> {
                        return@launch
                    }

                    _isShuffleEnabled.value -> {
                        val indices = queue.indices.filter { it != currentIndex }
                        if (indices.isNotEmpty()) {
                            val randomIndex = indices.random()
                            _currentIndex.value = randomIndex
                            mediaPlayer.playSong(queue[randomIndex])
                        }
                    }

                    currentIndex < queue.size - 1 -> {
                        val nextIndex = currentIndex + 1
                        _currentIndex.value = nextIndex
                        mediaPlayer.playSong(queue[nextIndex])
                    }

                    _repeatMode.value == RepeatMode.ALL -> {
                        _currentIndex.value = 0
                        mediaPlayer.playSong(queue[0])
                    }

                    _repeatMode.value == RepeatMode.ONE -> {
                        mediaPlayer.playSong(queue[currentIndex])
                    }

                    else -> {
                        return@launch
                    }
                }
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }

    fun playPrevious() {
        viewModelScope.launch {
            try {
                val queue = _queue.value
                val currentIndex = _currentIndex.value

                when {
                    queue.isEmpty() -> {
                        return@launch
                    }

                    currentIndex > 0 -> {
                        val previousIndex = currentIndex - 1
                        _currentIndex.value = previousIndex
                        mediaPlayer.playSong(queue[previousIndex])
                    }

                    _repeatMode.value == RepeatMode.ALL -> {
                        val lastIndex = queue.size - 1
                        _currentIndex.value = lastIndex
                        mediaPlayer.playSong(queue[lastIndex])
                    }

                    else -> {
                        mediaPlayer.seekTo(0)
                    }
                }
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }

    fun toggleShuffle() {
        viewModelScope.launch {
            try {
                _isShuffleEnabled.value = !_isShuffleEnabled.value

                val currentSong = _currentSong.value

                if (_isShuffleEnabled.value) {
                    val shuffledQueue =
                        shuffleQueueKeepingCurrent(_originalQueue.value, _currentIndex.value)
                    _queue.value = shuffledQueue
                    _currentIndex.value = 0
                } else {
                    _queue.value = _originalQueue.value

                    currentSong?.let { song ->
                        val originalIndex = _originalQueue.value.indexOfFirst { it.id == song.id }
                        if (originalIndex != -1) {
                            _currentIndex.value = originalIndex
                        }
                    }
                }
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }

    private fun shuffleQueueKeepingCurrent(songs: List<Cancion>, currentIndex: Int): List<Cancion> {
        if (songs.isEmpty()) return songs

        val currentSong = songs.getOrNull(currentIndex)
        val otherSongs = songs.toMutableList().apply {
            if (currentIndex in indices) removeAt(currentIndex)
        }.shuffled()

        return if (currentSong != null) {
            listOf(currentSong) + otherSongs
        } else {
            otherSongs
        }
    }

    fun toggleRepeat() {
        _repeatMode.value = when (_repeatMode.value) {
            RepeatMode.OFF -> RepeatMode.ALL
            RepeatMode.ALL -> RepeatMode.ONE
            RepeatMode.ONE -> RepeatMode.OFF
        }

    }

    override fun onCleared() {
        super.onCleared()
        try {
            mediaPlayer.release()
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
    }
}

enum class RepeatMode {
    OFF, ALL, ONE
}