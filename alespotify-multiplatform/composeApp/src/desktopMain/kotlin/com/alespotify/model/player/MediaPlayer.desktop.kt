package com.alespotify.model.player

import com.alespotify.model.Cancion
import com.alespotify.model.Playlist
import com.alespotify.model.User
import korlibs.audio.sound.Sound
import korlibs.audio.sound.SoundChannel
import korlibs.io.net.http.Http
import korlibs.io.net.http.HttpClient
import korlibs.io.stream.readAll
import korlibs.time.seconds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.absoluteValue

actual fun createMediaPlayer(): MediaPlayer = DesktopMediaPlayer()

class DesktopMediaPlayer : MediaPlayer {
    private var currentSound: Sound? = null
    private var currentSoundChannel: SoundChannel? = null
    private val scope = CoroutineScope(Dispatchers.Default)
    private val httpClient = HttpClient()

    // Play queue management
    private val _playQueue = MutableStateFlow<List<Cancion>>(emptyList())
    val playQueue: StateFlow<List<Cancion>> = _playQueue.asStateFlow()

    private val _currentIndex = MutableStateFlow(0)
    val currentIndex: StateFlow<Int> = _currentIndex.asStateFlow()

    private val _currentPlaylist = MutableStateFlow<Playlist?>(null)
    val currentPlaylist: StateFlow<Playlist?> = _currentPlaylist.asStateFlow()

    private val _isPlaying = MutableStateFlow(false)
    override val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _currentPosition = MutableStateFlow(0L)
    override val currentPosition: StateFlow<Long> = _currentPosition.asStateFlow()

    private val _duration = MutableStateFlow(0L)
    override val duration: StateFlow<Long> = _duration.asStateFlow()

    private val _currentSong = MutableStateFlow<Cancion?>(null)
    override val currentSong: StateFlow<Cancion?> = _currentSong.asStateFlow()

    // Shuffle and repeat modes
    private val _isShuffled = MutableStateFlow(false)
    val isShuffled: StateFlow<Boolean> = _isShuffled.asStateFlow()

    private val _repeatMode = MutableStateFlow(RepeatMode.OFF)
    val repeatMode: StateFlow<RepeatMode> = _repeatMode.asStateFlow()

    enum class RepeatMode { OFF, ALL, ONE }

    // Play a single song (creates temporary playlist)
    override suspend fun playSong(song: Cancion) {
        val tempPlaylist = Playlist(
            id = "temp_${System.currentTimeMillis()}",
            nombre = "Now Playing",
            user = User("temp_${System.currentTimeMillis()}", "temp", "", "temp_user"),
            songs = listOf(song),
            image = "",
            isPublic = true,
        )
        playPlaylist(tempPlaylist, 0)
    }

    // Play a playlist starting from a specific index
    override suspend fun playPlaylist(playlist: Playlist, startIndex: Int) {
        withContext(Dispatchers.Default) {
            try {
                _currentPlaylist.value = playlist
                _playQueue.value = playlist.songs!!
                _currentIndex.value = startIndex.coerceIn(0, playlist.songs.size - 1)

                val songToPlay = playlist.songs.getOrNull(_currentIndex.value)
                if (songToPlay != null) {
                    playCurrentSong()
                } else {
                    println("No song found at index $startIndex in playlist ${playlist.nombre}")
                }
            } catch (e: Exception) {
                println("Error playing playlist: ${e.message}")
            }
        }
    }

    override suspend fun nextSong(currentIndex: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun prevSong(currentIndex: Int) {
        TODO("Not yet implemented")
    }

    // Play the current song in the queue
    private suspend fun playCurrentSong() {
        val currentQueue = _playQueue.value
        val index = _currentIndex.value

        if (index < 0 || index >= currentQueue.size) {
            println("Invalid current index: $index")
            return
        }

        val song = currentQueue[index]
        playAudioFromUrl(song)
    }

    // The actual audio playing logic
    private suspend fun playAudioFromUrl(song: Cancion) {
        withContext(Dispatchers.Default) {
            try {
                // Stop any currently playing sound
                currentSoundChannel?.stop()
                currentSoundChannel = null
                currentSound = null

                println("Attempting to stream audio from: ${song.source}")

                // Download/stream the audio file from the remote server
                val audioData = downloadAudioData(song.source)

                if (audioData == null) {
                    println("Failed to download audio data from: ${song.source}")
                    _isPlaying.value = false
                    // Try to skip to next song
                    skipToNext()
                    return@withContext
                }

                // TODO: Create sound from audioData - this needs to be implemented
                // based on your Korge version's available audio API
                // For now, we'll simulate the behavior
                println("Would create sound from ${audioData.size} bytes")

                // Simulate sound creation and playback
                _currentSong.value = song
                _isPlaying.value = true
                _duration.value = 180000L // Simulate 3 minute duration

                // Simulate playback monitoring
                scope.launch {
                    var position = 0L
                    while (_isPlaying.value && isActive && position < _duration.value) {
                        delay(100)
                        position += 100
                        _currentPosition.value = position
                    }

                    // Song finished - handle next song
                    if (_isPlaying.value) {
                        handleSongEnd()
                    }
                }

                println("Successfully started playing song: ${song.name}")

            } catch (e: Exception) {
                println("Error playing song: ${e.message}")
                _isPlaying.value = false
                skipToNext()
            }
        }
    }

    private suspend fun downloadAudioData(url: String): ByteArray? {
        return try {
            println("Downloading audio from: $url")
            val response = httpClient.request(Http.Method.GET, url)

            if (response.status.absoluteValue in 200..299) {
                val audioData = response.content.readAll()
                println("Successfully downloaded ${audioData.size} bytes")
                audioData
            } else {
                println("HTTP Error: ${response.status}")
                null
            }
        } catch (e: Exception) {
            println("Error downloading audio: ${e.message}")
            null
        }
    }

    // Handle what happens when a song ends
    private suspend fun handleSongEnd() {
        when (_repeatMode.value) {
            RepeatMode.ONE -> {
                // Repeat current song
                playCurrentSong()
            }

            RepeatMode.ALL -> {
                // Move to next song, or loop back to first
                if (hasNext()) {
                    skipToNext()
                } else {
                    _currentIndex.value = 0
                    playCurrentSong()
                }
            }

            RepeatMode.OFF -> {
                // Move to next song if available
                if (hasNext()) {
                    skipToNext()
                } else {
                    // Playlist finished
                    _isPlaying.value = false
                    _currentPosition.value = 0L
                    println("Playlist finished")
                }
            }
        }
    }

    // Navigation methods
    suspend fun skipToNext() {
        if (hasNext()) {
            _currentIndex.value += 1
            playCurrentSong()
        } else if (_repeatMode.value == RepeatMode.ALL) {
            _currentIndex.value = 0
            playCurrentSong()
        }
    }

    suspend fun skipToPrevious() {
        if (hasPrevious()) {
            _currentIndex.value -= 1
            playCurrentSong()
        } else if (_repeatMode.value == RepeatMode.ALL) {
            _currentIndex.value = _playQueue.value.size - 1
            playCurrentSong()
        }
    }

    suspend fun skipToIndex(index: Int) {
        if (index in 0 until _playQueue.value.size) {
            _currentIndex.value = index
            playCurrentSong()
        }
    }

    fun hasNext(): Boolean = _currentIndex.value < _playQueue.value.size - 1
    fun hasPrevious(): Boolean = _currentIndex.value > 0

    // Shuffle functionality
    fun toggleShuffle() {
        _isShuffled.value = !_isShuffled.value
        if (_isShuffled.value) {
            shuffleQueue()
        } else {
            restoreOriginalOrder()
        }
    }

    private fun shuffleQueue() {
        val currentSong = _currentSong.value
        val shuffledQueue = _playQueue.value.shuffled()
        _playQueue.value = shuffledQueue

        // Update current index to match the current song's new position
        currentSong?.let { song ->
            val newIndex = shuffledQueue.indexOfFirst { it.id == song.id }
            if (newIndex != -1) {
                _currentIndex.value = newIndex
            }
        }
    }

    private fun restoreOriginalOrder() {
        _currentPlaylist.value?.let { playlist ->
            _playQueue.value = playlist.songs!!
            _currentSong.value?.let { song ->
                val newIndex = playlist.songs.indexOfFirst { it.id == song.id }
                if (newIndex != -1) {
                    _currentIndex.value = newIndex
                }
            }
        }
    }

    // Repeat mode cycling
    fun toggleRepeatMode() {
        _repeatMode.value = when (_repeatMode.value) {
            RepeatMode.OFF -> RepeatMode.ALL
            RepeatMode.ALL -> RepeatMode.ONE
            RepeatMode.ONE -> RepeatMode.OFF
        }
    }

    // Add song to current queue
    suspend fun addToQueue(song: Cancion) {
        val currentQueue = _playQueue.value.toMutableList()
        currentQueue.add(song)
        _playQueue.value = currentQueue
    }

    // Remove song from queue
    suspend fun removeFromQueue(index: Int) {
        if (index in 0 until _playQueue.value.size) {
            val currentQueue = _playQueue.value.toMutableList()
            currentQueue.removeAt(index)
            _playQueue.value = currentQueue

            // Adjust current index if needed
            if (index < _currentIndex.value) {
                _currentIndex.value -= 1
            } else if (index == _currentIndex.value && _currentIndex.value >= currentQueue.size) {
                _currentIndex.value = currentQueue.size - 1
            }
        }
    }

    // Clear queue
    suspend fun clearQueue() {
        stop()
        _playQueue.value = emptyList()
        _currentIndex.value = 0
        _currentPlaylist.value = null
    }

    // Basic playback controls
    override suspend fun play() {
        if (_currentSong.value != null) {
            withContext(Dispatchers.Default) {
                currentSoundChannel?.resume()
                _isPlaying.value = true
                println("Resumed playing")
            }
        } else if (_playQueue.value.isNotEmpty()) {
            playCurrentSong()
        }
    }

    override suspend fun pause() {
        withContext(Dispatchers.Default) {
            currentSoundChannel?.pause()
            _isPlaying.value = false
            println("Paused playing")
        }
    }

    override suspend fun stop() {
        withContext(Dispatchers.Default) {
            currentSoundChannel?.stop()
            currentSoundChannel = null
            _isPlaying.value = false
            _currentPosition.value = 0L
            println("Stopped playing")
        }
    }

    override suspend fun seekTo(position: Long) {
        println("SeekTo not fully supported in Korge's simple Sound API")
        _currentPosition.value = position
    }

    override suspend fun setVolume(volume: Float) {
        withContext(Dispatchers.Default) {
            currentSoundChannel?.volume = volume.toDouble()
            println("Volume set to: $volume")
        }
    }

    override fun release() {
        currentSoundChannel?.stop()
        currentSoundChannel = null
        currentSound = null
        println("Released media player resources")
    }
}