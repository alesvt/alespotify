package com.alespotify.model.player

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.MediaSource
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
    private val _exoPlayer = MutableStateFlow<ExoPlayer?>(null)
    val exoPlayer: StateFlow<ExoPlayer?> = _exoPlayer.asStateFlow()

    private var context: Context? = null
    private val scope = CoroutineScope(Dispatchers.Main)
    private var positionUpdateJob: Job? = null
    private var currentPlaylist: List<Cancion> = emptyList()
    private var currentIndex: Int = 0

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
        if (exoPlayer.value == null) {
            try {
                _exoPlayer.value = ExoPlayer.Builder(context).build().apply {
                    addListener(object : Player.Listener {
                        override fun onIsPlayingChanged(isPlaying: Boolean) {
                            _isPlaying.value = isPlaying
                        }

                        override fun onPlaybackStateChanged(playbackState: Int) {
                            when (playbackState) {
                                Player.STATE_READY -> {
                                    _duration.value =
                                        this@apply.duration // 'this@apply' es el ExoPlayer
                                }
                            }
                        }

                        override fun onPlayerError(error: androidx.media3.common.PlaybackException) {
                            error.printStackTrace()
                        }
                    })
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override suspend fun playSong(song: Cancion) {

        try {
            println(_exoPlayer.value) // aqui sale null
            _exoPlayer.value.let { player ->
                val mediaItem = MediaItem.fromUri(song.source)
                player?.setMediaItem(mediaItem)
                player?.prepare()
                player?.play()
                _currentSong.value = song

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override suspend fun play() {
        exoPlayer.value?.play()
    }

    override suspend fun pause() {
        exoPlayer.value?.pause()
    }

    override suspend fun stop() {
        exoPlayer.value?.stop()
        _currentPosition.value = 0L
    }

    override suspend fun seekTo(position: Long) {
        exoPlayer.value?.seekTo(position)
        _currentPosition.value = position
    }

    override suspend fun setVolume(volume: Float) {
        exoPlayer.value?.volume = volume.coerceIn(0f, 1f)
    }

    override suspend fun playPlaylist(playlist: Playlist, startIndex: Int) {
        playlist.songs?.let { songs ->
            currentPlaylist = songs
            currentIndex = startIndex.coerceIn(0, songs.size - 1)
            playSong(songs[currentIndex])
        }
    }

    override suspend fun nextSong(currentIndex: Int) {
        if (currentPlaylist.isNotEmpty() && currentIndex < currentPlaylist.size - 1) {
            this.currentIndex = currentIndex + 1
            playSong(currentPlaylist[this.currentIndex])
        }
    }

    override suspend fun prevSong(currentIndex: Int) {
        if (currentPlaylist.isNotEmpty() && currentIndex > 0) {
            this.currentIndex = currentIndex - 1
            playSong(currentPlaylist[this.currentIndex])
        }
    }

    override fun release() {
        stopPositionUpdates()
        exoPlayer.value?.release()

        currentPlaylist = emptyList()
        currentIndex = 0
    }


    private fun stopPositionUpdates() {
        positionUpdateJob?.cancel()
    }
}