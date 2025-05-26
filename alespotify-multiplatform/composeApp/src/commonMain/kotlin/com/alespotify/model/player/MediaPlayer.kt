package com.alespotify.model.player

import com.alespotify.model.Cancion
import com.alespotify.model.Playlist
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
    suspend fun setVolume(volume: Float)
    suspend fun playPlaylist(playlist: Playlist, startIndex: Int)
    suspend fun nextSong(currentIndex: Int)
    suspend fun prevSong(currentIndex: Int)


    fun release()
}

expect fun createMediaPlayer(): MediaPlayer