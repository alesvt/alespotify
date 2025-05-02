package com.alespotify.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import com.alespotify.ui.MyColors
import com.alespotify.ui.navigation.AppViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

// androidMain
@Preview
@Composable
actual fun Player(
    isPlaying: Boolean,
    onPlayPauseClick: () -> Unit,
    onCollapseClick: () -> Unit,
    appViewModel: AppViewModel
) {
    val isPlaying = remember { mutableStateOf(false) }
    val currentTab = remember { mutableStateOf("home") }
    val expandPlayer = remember { mutableStateOf(false) }
    val currentSlideIndex = remember { mutableStateOf(0) }
    val sliderState = rememberLazyListState()

    val song = appViewModel.currentSong.collectAsState()
    val sliderPosition = remember { mutableStateOf(38f) }



    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
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

// fondo
        Image(
            painter = rememberAsyncImagePainter("https://upload.wikimedia.org/wikipedia/en/e/e6/The_Weeknd_-_Blinding_Lights.png"),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alpha = 0.3f,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {


            // Album art
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
                        painter = rememberAsyncImagePainter("https://upload.wikimedia.org/wikipedia/en/e/e6/The_Weeknd_-_Blinding_Lights.png"), // Usando una URL de placeholder
                        contentDescription = "Album cover",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
            // Song info
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(start = 24.dp, end = 24.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Blinding Lights",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "The Weeknd",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
            // Playback controls
            Column(
                modifier = Modifier
                    .padding(start = 32.dp, end = 32.dp, bottom = 160.dp)
            ) {
                // Progress bar
                Slider(
                    value = sliderPosition.value,
                    onValueChange = { sliderPosition.value = it },
                    valueRange = 0f..100f,
                    steps = 99,
                    colors = SliderDefaults.colors(
                        thumbColor = Color.White,
                        activeTrackColor = Color.White,
                        inactiveTrackColor = Color.White.copy(alpha = 0.3f)
                    ),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Time indicators
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp)
                ) {
                    Text("1:42", color = Color.Gray, fontSize = 15.sp)
                    Text("4:22", color = Color.Gray, fontSize = 15.sp)
                }

                // Control buttons
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(onClick = { /* TODO: Implement shuffle */ }) {
                        Icon(
                            Icons.Filled.Shuffle,
                            contentDescription = "Shuffle",
                            tint = Color.Gray
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = { /* TODO: Implement skip back */ },
                            modifier = Modifier.size(52.dp)
                        ) {
                            Icon(
                                Icons.Filled.SkipPrevious,
                                contentDescription = "Skip Back",
                                tint = Color.White
                            )
                        }



                        Button(
                            onClick = { onPlayPauseClick() },
                            shape = RoundedCornerShape(28.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.White,
                                contentColor = Color.Black
                            ),
                            modifier = Modifier.size(56.dp)
                        ) {
                            Icon(
                                if (isPlaying.value) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                                contentDescription = "play/pause",
                                modifier = Modifier.size(32.dp)
                            )
                        }
                        IconButton(
                            onClick = { /* TODO: Implement skip forward */ },
                            modifier = Modifier.size(52.dp)
                        ) {
                            Icon(
                                Icons.Filled.SkipNext,
                                contentDescription = "Skip Forward",
                                tint = Color.White
                            )
                        }
                    }

                    IconButton(onClick = { /* TODO: Implement repeat */ }) {
                        Icon(
                            Icons.Filled.Repeat,
                            contentDescription = "Repeat",
                            tint = Color.Gray
                        )
                    }
                }
            }
        }
    }
}


