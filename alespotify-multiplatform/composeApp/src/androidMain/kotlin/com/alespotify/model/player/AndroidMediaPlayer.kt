package com.alespotify.model.player

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.alespotify.model.Cancion
import com.alespotify.model.Playlist
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

actual fun createMediaPlayer(): MediaPlayer = AndroidMediaPlayer()
class AndroidMediaPlayer : MediaPlayer {
    private var exoPlayer: ExoPlayer? = null
    private var context: Context? = null
    private val scope = CoroutineScope(Dispatchers.Main)
    private var positionUpdateJob: Job? = null

    private val _isPlaying = MutableStateFlow(false)
    override val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _currentPosition = MutableStateFlow(0L)
    override val currentPosition: StateFlow<Long> = _currentPosition.asStateFlow()

    private val _duration = MutableStateFlow(0L)
    override val duration: StateFlow<Long> = _duration.asStateFlow()

    private val _currentSong = MutableStateFlow<Cancion?>(null)
    override val currentSong: StateFlow<Cancion?> = _currentSong.asStateFlow()

    fun initialize(context: Context) {
        this.context = context
        if (exoPlayer == null) {
            exoPlayer = ExoPlayer.Builder(context).build().apply {
                addListener(object : Player.Listener {
                    override fun onIsPlayingChanged(isPlaying: Boolean) {
                        _isPlaying.value = isPlaying
                        if (isPlaying) {
                            startPositionUpdates()
                        } else {
                            stopPositionUpdates()
                        }
                    }

                    override fun onPlaybackStateChanged(playbackState: Int) {
                        if (playbackState == Player.STATE_READY) {
                            _duration.value = duration
                        }
                    }
                })
            }
        }
    }

    override suspend fun playSong(song: Cancion) {
        exoPlayer?.let { player ->
            val mediaItem = MediaItem.fromUri(song.source)
            player.setMediaItem(mediaItem)
            player.prepare()
            player.play()
            _currentSong.value = song
        }
    }

    override suspend fun play() {
        exoPlayer?.play()
    }

    override suspend fun pause() {
        exoPlayer?.pause()
    }

    override suspend fun stop() {
        exoPlayer?.stop()
        _currentPosition.value = 0L
    }

    override suspend fun seekTo(position: Long) {
        exoPlayer?.seekTo(position)
        _currentPosition.value = position
    }

    override suspend fun setVolume(volume: Float) {
        exoPlayer?.volume = volume
    }

    override suspend fun playPlaylist(playlist: Playlist, startIndex: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun nextSong(currentIndex: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun prevSong(currentIndex: Int) {
        TODO("Not yet implemented")
    }

    override fun release() {
        stopPositionUpdates()
        exoPlayer?.release()
        exoPlayer = null
    }

    private fun startPositionUpdates() {
        positionUpdateJob?.cancel()
        positionUpdateJob = scope.launch {
            while (isActive && exoPlayer?.isPlaying == true) {
                _currentPosition.value = exoPlayer?.currentPosition ?: 0L
                delay(1000)
            }
        }
    }

    private fun stopPositionUpdates() {
        positionUpdateJob?.cancel()
    }
}