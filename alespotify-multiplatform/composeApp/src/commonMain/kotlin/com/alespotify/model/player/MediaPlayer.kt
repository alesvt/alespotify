package com.alespotify.model.player

import com.alespotify.model.Cancion
import kotlinx.coroutines.flow.StateFlow

interface MediaPlayer {
    val isPlaying: StateFlow<Boolean>
    val currentPosition: StateFlow<Long>
    val duration: StateFlow<Long>
    val currentSong: StateFlow<Cancion?>

    suspend fun playSong(song: Cancion)
    suspend fun play()
    suspend fun pause()
    suspend fun stop()
    suspend fun seekTo(position: Long)
    suspend fun setVolume(volume: Float) // 0.0f to 1.0f
    fun release()
}

expect fun createMediaPlayer() : MediaPlayer