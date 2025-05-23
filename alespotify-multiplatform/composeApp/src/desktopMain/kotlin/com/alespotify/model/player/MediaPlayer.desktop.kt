package com.alespotify.model.player

import com.alespotify.model.Cancion
import korlibs.audio.sound.Sound

import korlibs.audio.sound.AudioChannel
import korlibs.audio.sound.SoundChannel
import korlibs.audio.sound.playing

import korlibs.korge.Korge
import korlibs.audio.sound.readSound
import korlibs.io.file.std.resourcesVfs
import korlibs.time.seconds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

actual fun createMediaPlayer(): MediaPlayer = DesktopMediaPlayer()

class DesktopMediaPlayer : MediaPlayer {
    private var currentSound: Sound? = null
    private var currentSoundChannel: SoundChannel? = null
    private val scope =
        CoroutineScope(Dispatchers.Default) // Korge typically uses Default or Main for UI/game loop

    private val _isPlaying = MutableStateFlow(false)
    override val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    // Korge's Sound API doesn't easily expose current position or duration
    // as reactive flows like JavaFX does. These will be limited or not updated.
    private val _currentPosition = MutableStateFlow(0L)
    override val currentPosition: StateFlow<Long> = _currentPosition.asStateFlow()

    private val _duration = MutableStateFlow(0L)
    override val duration: StateFlow<Long> = _duration.asStateFlow()

    private val _currentSong = MutableStateFlow<Cancion?>(null)
    override val currentSong: StateFlow<Cancion?> = _currentSong.asStateFlow()

    override suspend fun playSong(song: Cancion) {
        withContext(Dispatchers.Default) { // Korge operations can be on Default or Main
            try {
                // Stop any currently playing sound
                currentSoundChannel?.stop()
                currentSoundChannel = null
                currentSound = null

                // Load the sound from resources.
                // Assuming song.source is a path like "music/my_song.mp3"
                // Korge loads resources from the 'resources' folder of your project.
                val sound = resourcesVfs[song.source].readSound()
                currentSound = sound

                _duration.value = (sound.length.seconds * 1000).toLong() // Approximate duration

                currentSoundChannel = sound.play().also { channel ->
                    // Korge Sound.Channel doesn't have direct "onEnded" callbacks
                    // but you could poll its .playing state or set a job to delay
                    // for its duration and then update _isPlaying.
                    // For simplicity, we'll just set playing state directly.
                    _isPlaying.value = true
                    _currentSong.value = song

                    // rudimentary end-of-media detection (not precise for seeking)
                    scope.launch {
                        currentSoundChannel?.let { channel ->
                            _isPlaying.value = true // Set playing state when starting

                            // Poll the 'playing' status
                            while (channel.playing && isActive) { // 'isActive' for coroutine cancellation
                                // You can also update _currentPosition here, though it's an estimate
                                // _currentPosition.value = (channel.currentTime.seconds * 1000).toLong()
                                delay(100) // Adjust polling interval as needed
                            }
                            _isPlaying.value = false
                            _currentPosition.value = 0L // Reset after completion
                            println("End of media reached (Korge)")
                        }
                    }
                }
                println("Playing song (Korge): ${song.name}")

            } catch (e: Exception) {
                println("Error playing song (Korge): ${e.message}")
                _isPlaying.value = false
            }
        }
    }

    override suspend fun play() {
        withContext(Dispatchers.Default) {
            currentSoundChannel?.resume() // Korge Sound.Channel has resume/pause
            _isPlaying.value = true
            println("Resumed playing (Korge)")
        }
    }

    override suspend fun pause() {
        withContext(Dispatchers.Default) {
            currentSoundChannel?.pause() // Korge Sound.Channel has resume/pause
            _isPlaying.value = false
            println("Paused playing (Korge)")
        }
    }

    override suspend fun stop() {
        withContext(Dispatchers.Default) {
            currentSoundChannel?.stop()
            currentSoundChannel = null
            _isPlaying.value = false
            _currentPosition.value = 0L // Reset on stop
            println("Stopped playing (Korge)")
        }
    }

    override suspend fun seekTo(position: Long) {
        // Korge's Sound.Channel doesn't directly support seeking to an arbitrary millisecond.
        // You would typically stop and re-play from an offset if this was needed,
        // but it's not a direct equivalent to JavaFX's seek.
        // For simple background music, this might not be critical.
        println("SeekTo not fully supported in Korge's simple Sound API for precise control.")
        // If precise seeking is critical, you would need a more advanced audio library.
        _currentPosition.value = position // Still update the UI state
    }

    override suspend fun setVolume(volume: Float) {
        withContext(Dispatchers.Default) {
            currentSoundChannel?.volume = volume.toDouble()
            println("Volume set to: $volume (Korge)")
        }
    }

    override fun release() {
        // Korge sounds are typically managed by the game engine.
        // We just stop the channel.
        currentSoundChannel?.stop()
        currentSoundChannel = null
        currentSound = null
        println("Released Korge media player resources")
    }


}