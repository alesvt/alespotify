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
import androidx.compose.ui.tooling.preview.Preview
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

    val currentSong by queueViewModel.currentSong.collectAsState()
    val isPlayingState by queueViewModel.isPlaying.collectAsState()
    val currentPosition by queueViewModel.currentPosition.collectAsState()
    val duration by queueViewModel.duration.collectAsState()
    val isShuffleEnabled by queueViewModel.isShuffleEnabled.collectAsState()
    val repeatMode by queueViewModel.repeatMode.collectAsState()


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        currentSong?.image?.let { imageUrl ->
            Image(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                alpha = 0.4f,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.6f))
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 20.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(
                onClick = onCollapseClick,
                modifier = Modifier.clip(CircleShape)
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack, "volver atrás",
                    tint = Color.White,
                    modifier = Modifier
                        .background(Color(0x553b3b3b))
                        .padding(8.dp)
                        .size(24.dp)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(0.5f))


            Card(
                shape = RoundedCornerShape(12.dp),
                elevation = 12.dp,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
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

            Spacer(modifier = Modifier.height(32.dp))


            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = currentSong?.name ?: "Sin canción",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = currentSong?.artists?.firstOrNull()?.name ?: "Artista desconocido",
                    fontSize = 16.sp,
                    color = Color.LightGray,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.weight(0.5f))


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 60.dp)
            ) {

                Slider(
                    value = if(duration > 0) currentPosition.toFloat() / duration.toFloat() else 0f,
                    onValueChange = { progress ->
                        if (duration > 0) {
                            val newPosition = (progress * duration).toLong()
                            queueViewModel.seekTo(newPosition)
                        }
                    },
                    colors = SliderDefaults.colors(
                        thumbColor = Color.White,
                        activeTrackColor = MyColors.primary,
                        inactiveTrackColor = Color.White.copy(alpha = 0.3f)
                    ),
                    modifier = Modifier.padding(horizontal = 0.dp)
                )


                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp)
                ) {
                    Text(
                        text = formatTime(currentPosition),
                        color = Color.LightGray,
                        fontSize = 14.sp
                    )
                    Text(
                        text = formatTime(duration),
                        color = Color.LightGray,
                        fontSize = 14.sp
                    )
                }


                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(
                        onClick = { queueViewModel.toggleShuffle() },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            Icons.Filled.Shuffle,
                            contentDescription = "Shuffle",
                            tint = if (isShuffleEnabled) MyColors.primary else Color.Gray,
                            modifier = Modifier.size(28.dp)
                        )
                    }


                    IconButton(
                        onClick = { queueViewModel.playPrevious() },
                        modifier = Modifier.size(64.dp)
                    ) {
                        Icon(
                            Icons.Filled.SkipPrevious,
                            contentDescription = "Skip Back",
                            tint = Color.White,
                            modifier = Modifier.size(40.dp)
                        )
                    }


                    Button(
                        onClick = { queueViewModel.playPause() },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.White,
                            contentColor = Color.Black
                        ),
                        modifier = Modifier.size(72.dp)
                    ) {
                        Icon(
                            if (isPlayingState) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                            contentDescription = "play/pause",
                            modifier = Modifier.size(48.dp)
                        )
                    }


                    IconButton(
                        onClick = { queueViewModel.playNext() },
                        modifier = Modifier.size(64.dp)
                    ) {
                        Icon(
                            Icons.Filled.SkipNext,
                            contentDescription = "Skip Forward",
                            tint = Color.White,
                            modifier = Modifier.size(40.dp)
                        )
                    }


                    IconButton(
                        onClick = { queueViewModel.toggleRepeat() },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            when (repeatMode) {
                                RepeatMode.ONE -> Icons.Filled.RepeatOne
                                else -> Icons.Filled.Repeat
                            },
                            contentDescription = "Repeat",
                            tint = if (repeatMode != RepeatMode.OFF) MyColors.primary else Color.Gray,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }
        }
    }
}