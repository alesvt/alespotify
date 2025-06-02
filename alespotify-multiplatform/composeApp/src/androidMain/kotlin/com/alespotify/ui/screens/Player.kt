package com.alespotify.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import com.alespotify.ui.MyColors
import com.alespotify.ui.navigation.AppViewModel
import com.alespotify.ui.navigation.QueueViewModel
import com.alespotify.ui.navigation.RepeatMode

@Composable
actual fun Player(
    isPlaying: Boolean,
    onPlayPauseClick: () -> Unit,
    onCollapseClick: () -> Unit,
    appViewModel: AppViewModel,
    queueViewModel: QueueViewModel
) {
    // Estados del reproductor
    val currentSong by queueViewModel.currentSong.collectAsState()
    val isPlayingState by queueViewModel.isPlaying.collectAsState()
    val currentPosition by queueViewModel.currentPosition.collectAsState()
    val duration by queueViewModel.duration.collectAsState()
    val isShuffleEnabled by queueViewModel.isShuffleEnabled.collectAsState()
    val repeatMode by queueViewModel.repeatMode.collectAsState()

    // Estado local para el slider
    var sliderPosition by remember { mutableStateOf(0f) }
    var isDragging by remember { mutableStateOf(false) }

    // Actualizar posición del slider
    LaunchedEffect(currentPosition, duration) {
        if (!isDragging && duration > 0) {
            sliderPosition = (currentPosition.toFloat() / duration.toFloat()) * 100f
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Botón de volver
        Row(
            Modifier
                .background(Color.Transparent)
                .padding(20.dp)
        ) {
            IconButton(
                onClick = onCollapseClick,
                Modifier.clip(CircleShape)
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack, "volver atrás",
                    tint = Color.White,
                    modifier = Modifier
                        .background(Color(0xaa3b3b3b))
                        .padding(5.dp)
                )
            }
        }

        // Imagen de fondo
        currentSong?.image?.let { imageUrl ->
            Image(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                alpha = 0.3f,
                modifier = Modifier.fillMaxSize()
            )
        }

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Arte del álbum
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(start = 25.dp, end = 25.dp),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    shape = RoundedCornerShape(10.dp),
                    elevation = 8.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            currentSong?.image ?: "https://via.placeholder.com/400"
                        ),
                        contentDescription = "Album cover",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            // Información de la canción
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(start = 24.dp, end = 24.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = currentSong?.name ?: "Sin canción",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = currentSong?.artists?.firstOrNull()?.name ?: "Artista desconocido",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            // Controles de reproducción
            Column(
                modifier = Modifier
                    .padding(start = 32.dp, end = 32.dp, bottom = 160.dp)
            ) {
                // Barra de progreso
                Slider(
                    value = sliderPosition,
                    onValueChange = {
                        sliderPosition = it
                        isDragging = true
                    },
                    onValueChangeFinished = {
                        isDragging = false
                        val newPosition = (sliderPosition / 100f * duration).toLong()
                        queueViewModel.seekTo(newPosition)
                    },
                    valueRange = 0f..100f,
                    colors = SliderDefaults.colors(
                        thumbColor = Color.White,
                        activeTrackColor = Color.White,
                        inactiveTrackColor = Color.White.copy(alpha = 0.3f)
                    ),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Indicadores de tiempo
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp)
                ) {
                    Text(
                        queueViewModel.formatTime(currentPosition),
                        color = Color.Gray,
                        fontSize = 15.sp
                    )
                    Text(
                        queueViewModel.formatTime(duration),
                        color = Color.Gray,
                        fontSize = 15.sp
                    )
                }

                // Botones de control
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Shuffle
                    IconButton(onClick = { queueViewModel.toggleShuffle() }) {
                        Icon(
                            Icons.Filled.Shuffle,
                            contentDescription = "Shuffle",
                            tint = if (isShuffleEnabled) MyColors.primary else Color.Gray
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Anterior
                        IconButton(
                            onClick = { queueViewModel.playPrevious() },
                            modifier = Modifier.size(52.dp)
                        ) {
                            Icon(
                                Icons.Filled.SkipPrevious,
                                contentDescription = "Skip Back",
                                tint = Color.White
                            )
                        }

                        // Play/Pause
                        Button(
                            onClick = { queueViewModel.playPause() },
                            shape = RoundedCornerShape(28.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.White,
                                contentColor = Color.Black
                            ),
                            modifier = Modifier.size(56.dp)
                        ) {
                            Icon(
                                if (isPlayingState) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                                contentDescription = "play/pause",
                                modifier = Modifier.size(32.dp)
                            )
                        }

                        // Siguiente
                        IconButton(
                            onClick = { queueViewModel.playNext() },
                            modifier = Modifier.size(52.dp)
                        ) {
                            Icon(
                                Icons.Filled.SkipNext,
                                contentDescription = "Skip Forward",
                                tint = Color.White
                            )
                        }
                    }

                    // Repeat
                    IconButton(onClick = { queueViewModel.toggleRepeat() }) {
                        Icon(
                            when (repeatMode) {
                                RepeatMode.ONE -> Icons.Filled.RepeatOne
                                else -> Icons.Filled.Repeat
                            },
                            contentDescription = "Repeat",
                            tint = if (repeatMode != RepeatMode.OFF) MyColors.primary else Color.Gray
                        )
                    }
                }
            }
        }
    }
}