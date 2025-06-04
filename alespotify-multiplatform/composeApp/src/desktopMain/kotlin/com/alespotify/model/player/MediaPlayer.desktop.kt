package com.alespotify.model.player

import com.alespotify.model.Cancion
import com.alespotify.model.Playlist
import uk.co.caprica.vlcj.factory.MediaPlayerFactory
import uk.co.caprica.vlcj.player.base.MediaPlayer
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

actual fun createMediaPlayer(): com.alespotify.model.player.MediaPlayer = DesktopMediaPlayer()


class DesktopMediaPlayer : com.alespotify.model.player.MediaPlayer {
    private val mediaPlayerFactory = MediaPlayerFactory()
    private val vlcMediaPlayer = mediaPlayerFactory.mediaPlayers().newEmbeddedMediaPlayer()
    private val scope = CoroutineScope(Dispatchers.Default)

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


    private var isMediaReady = false
    private var pendingSeekPosition: Long? = null


    var onSongFinished: (() -> Unit)? = null

    init {
        setupVLCEventHandlers()
        startPositionUpdater()
    }

    private fun setupVLCEventHandlers() {
        vlcMediaPlayer.events().addMediaPlayerEventListener(object : MediaPlayerEventAdapter() {

            override fun playing(mediaPlayer: MediaPlayer) {
                _isPlaying.value = true
                isMediaReady = true

                val duration = mediaPlayer.status().length()
                if (duration > 0) {
                    _duration.value = duration
                }

                pendingSeekPosition?.let { position ->
                    mediaPlayer.controls().setTime(position)
                    _currentPosition.value = position
                    pendingSeekPosition = null
                }
            }

            override fun paused(mediaPlayer: MediaPlayer) {
                _isPlaying.value = false
            }

            override fun stopped(mediaPlayer: MediaPlayer) {
                _isPlaying.value = false
                _currentPosition.value = 0L
                isMediaReady = false
            }

            override fun finished(mediaPlayer: MediaPlayer) {
                _isPlaying.value = false
                onSongFinished?.invoke()
            }

            override fun error(mediaPlayer: MediaPlayer) {
                _isPlaying.value = false
                isMediaReady = false
            }

            override fun timeChanged(mediaPlayer: MediaPlayer, newTime: Long) {
                if (isMediaReady && newTime >= 0) {
                    _currentPosition.value = newTime
                }
            }

            override fun lengthChanged(mediaPlayer: MediaPlayer, newLength: Long) {
                if (newLength > 0) {
                    _duration.value = newLength
                }
            }
        })
    }

    private fun startPositionUpdater() {
        scope.launch {
            while (isActive) {
                try {
                    if (_isPlaying.value && isMediaReady && vlcMediaPlayer.status().isPlaying) {
                        val currentTime = vlcMediaPlayer.status().time()
                        val currentLength = vlcMediaPlayer.status().length()

                        if (currentTime >= 0) {
                            _currentPosition.value = currentTime
                        }

                        if (currentLength > 0 && _duration.value != currentLength) {
                            _duration.value = currentLength
                        }
                    }
                } catch (e: Exception) {
                    println("Error updating position: ${e.message}")
                }
                delay(500) // Actualizar cada 500ms
            }
        }
    }

    override suspend fun playSong(song: Cancion) {
        withContext(Dispatchers.IO) {
            try {
                _currentSong.value = song
                // Detener reproducción actual
                vlcMediaPlayer.controls().stop()
                // Limpiar estados
                _currentPosition.value = 0L
                _duration.value = 0L
                isMediaReady = false
                pendingSeekPosition = null

                val success = vlcMediaPlayer.media().play(song.source)

                if (!success) {
                    _isPlaying.value = false
                }
            } catch (e: Exception) {
                println("Error: ${e.message}")
                _isPlaying.value = false
            }
        }
    }

    override suspend fun playPlaylist(playlist: Playlist, startIndex: Int) {
        withContext(Dispatchers.IO) {
            try {
                _currentPlaylist.value = playlist
                _playQueue.value = playlist.songs ?: emptyList()
                _currentIndex.value = startIndex.coerceIn(0, (playlist.songs?.size ?: 1) - 1)

                val songToPlay = playlist.songs?.getOrNull(_currentIndex.value)
                if (songToPlay != null) {
                    playSong(songToPlay)
                }
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }

    override suspend fun nextSong(currentIndex: Int) {

    }

    override suspend fun prevSong(currentIndex: Int) {

    }

    override suspend fun play() {
        withContext(Dispatchers.IO) {
            try {
                if (vlcMediaPlayer.status().isPlayable) {
                    vlcMediaPlayer.controls().play()
                    println("VLC: Resumed playback")
                } else {
                    println("VLC: Media not playable, cannot resume")
                    // Si no hay media cargada pero tenemos una canción actual, intentar reproducirla
                    _currentSong.value?.let { song ->
                        playSong(song)
                    }
                }
            } catch (e: Exception) {
                println("Error resuming playback: ${e.message}")
            }
        }
    }

    override suspend fun pause() {
        withContext(Dispatchers.IO) {
            try {
                if (vlcMediaPlayer.status().isPlaying) {
                    vlcMediaPlayer.controls().pause()
                    println("VLC: Paused playback")
                } else {
                    println("VLC: Not currently playing, cannot pause")
                }
            } catch (e: Exception) {
                println("Error pausing playback: ${e.message}")
            }
        }
    }

    override suspend fun stop() {
        withContext(Dispatchers.IO) {
            try {
                vlcMediaPlayer.controls().stop()
                _currentPosition.value = 0L
                _isPlaying.value = false
                isMediaReady = false
                println("VLC: Stopped playback")
            } catch (e: Exception) {
                println("Error stopping playback: ${e.message}")
            }
        }
    }

    override suspend fun seekTo(position: Long) {
        withContext(Dispatchers.IO) {
            try {
                val clampedPosition = position.coerceAtLeast(0L)

                if (isMediaReady && vlcMediaPlayer.status().length() > 0) {
                    val maxPosition = vlcMediaPlayer.status().length()
                    val seekPosition = clampedPosition.coerceAtMost(maxPosition)

                    vlcMediaPlayer.controls().setTime(seekPosition)
                    _currentPosition.value = seekPosition
                } else {
                    pendingSeekPosition = clampedPosition
                    _currentPosition.value = clampedPosition
                }
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }

    override suspend fun setVolume(volume: Float) {
        withContext(Dispatchers.IO) {
            try {
                val vlcVolume = (volume * 100).toInt().coerceIn(0, 100)
                vlcMediaPlayer.audio().setVolume(vlcVolume)
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }

    override fun release() {
        try {

            vlcMediaPlayer.controls().stop()
            vlcMediaPlayer.release()
            mediaPlayerFactory.release()
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
    }

}