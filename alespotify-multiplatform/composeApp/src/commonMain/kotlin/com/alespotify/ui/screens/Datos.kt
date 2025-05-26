package com.alespotify.ui.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.automirrored.filled.*
import coil3.compose.rememberAsyncImagePainter

import org.jetbrains.compose.resources.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.alespotify.model.Cancion
import com.alespotify.model.User
import com.alespotify.ui.MyColors
import com.alespotify.ui.navigation.AppViewModel
import com.alespotify.ui.navigation.DestinosNavegacion
import com.alespotify.ui.navigation.LoginViewModel
import kotlinx.coroutines.flow.StateFlow
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import coil3.compose.AsyncImage
import com.alespotify.model.Artist
import com.alespotify.model.Playlist
import com.alespotify.shared.ApiService
import com.alespotify.ui.navigation.QueueViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
expect fun DatosScreen(
    navController: NavHostController,
    appViewModel: AppViewModel = AppViewModel(),
    loginViewModel: LoginViewModel = LoginViewModel(),
    queueViewModel: QueueViewModel = QueueViewModel(),
    apiService: ApiService
)

@OptIn(ExperimentalResourceApi::class)
@Composable
fun NavigationLink(icon: ImageVector, text: String, selected: Boolean = false) {
    val backgroundColor =
        if (selected) MaterialTheme.colors.primary.copy(alpha = 0.1f) else Color.Transparent
    val textColor =
        if (selected) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface.copy(alpha = 0.8f)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .background(backgroundColor, shape = MaterialTheme.shapes.medium)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = textColor,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = text, color = textColor, fontSize = 14.sp)
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun Avatar(fallbackText: String, imagePath: String) {
    Box(modifier = Modifier.size(40.dp)) {
        Image(
            painter = rememberAsyncImagePainter(imagePath),
            contentDescription = "User Avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize().clip(CircleShape)
        )
        if (imagePath.isEmpty()) {
            Text(text = fallbackText, modifier = Modifier.align(Alignment.Center))
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun FeaturedCard(title: String, description: String, imagePath: String) {
    Card(modifier = Modifier) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                rememberAsyncImagePainter(imagePath),
                contentDescription = "Featured Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth().height(200.dp)
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(text = description, color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
                Button(onClick = { }, modifier = Modifier.padding(top = 8.dp)) {
                    Text("Reproducir")
                }
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun RecentlyPlayedCard() {
    Card {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Recently Played",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                List(3) { it + 1 }.forEach { index ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Image(
                            rememberAsyncImagePainter("https://cdn-images.dzcdn.net/images/cover/b159f9470a45ca0ecda42062136ac33a/0x1900-000000-80-0-0.jpg"),
                            contentDescription = "Track $index",
                            modifier = Modifier.size(48.dp).clip(MaterialTheme.shapes.medium),
                            contentScale = ContentScale.Crop
                        )
                        Column {
                            Text(text = "Track Title $index", fontWeight = FontWeight.Medium)
                            Text(
                                text = "Artist Name",
                                fontSize = 12.sp,
                                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                            )
                        }
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalResourceApi::class)
@Composable
fun TopArtistsCard(artist: Artist) {
    Card {
        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                rememberAsyncImagePainter(artist.image),
                contentDescription = "Imagen ${artist.name}",
                modifier = Modifier.aspectRatio(1f)
                    .height(180.dp)
                    .width(180.dp),
                contentScale = ContentScale.Crop
            )
        }
        Column(
            modifier = Modifier.background(
                Brush.verticalGradient(listOf(MyColors.background, Color(0x616161)))
            )
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = artist.name,
                fontWeight = FontWeight.Medium
            )

        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun MadeForYouCard(playlist: Playlist) {
    Card {
        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                rememberAsyncImagePainter(playlist.image),
                contentDescription = "Playlist ${playlist.nombre}",
                modifier = Modifier.aspectRatio(1f)
                    .height(230.dp)
                    .width(230.dp),
                contentScale = ContentScale.Crop
            )
            IconButton(
                onClick = { /* todo play playlist*/ },
                modifier = Modifier.align(Alignment.BottomEnd)
            ) {
                Icon(imageVector = Icons.Filled.PlayArrow, contentDescription = "Play")
            }
        }
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = playlist.nombre, fontWeight = FontWeight.Medium)
            Text(
                text = "By ${playlist.user.name}",
                fontSize = 12.sp,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun NewReleaseCard(song: Cancion, queueViewModel: QueueViewModel) {
    Card(modifier = Modifier.width(180.dp)) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                rememberAsyncImagePainter(song.image),
                contentDescription = "New Release ${song.name}",
                modifier = Modifier.aspectRatio(1f),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp)
                    .clip(CircleShape)

            ) {
                IconButton(
                    modifier = Modifier.background(MyColors.primary)
                        .clip(CircleShape),
                    onClick = {
                        queueViewModel.playSong(song)
                    }
                )
                {
                    Icon(Icons.Filled.PlayArrow, contentDescription = "Play", tint = Color.White)
                }

            }
        }

        Column(
            modifier = Modifier
                .background(
                    Brush.verticalGradient(listOf(MyColors.background, Color(0x616161)))
                )
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = song.name,
                fontWeight = FontWeight.Medium
            )
            song.artists?.get(0)?.let {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = it.name,
                    fontSize = 12.sp,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                )
            }

        }

    }
}

@Composable
fun PlayerControls(
    queueViewModel: QueueViewModel,
    modifier: Modifier = Modifier
) {
    val isPlaying by queueViewModel.isPlaying.collectAsState()
    val currentSong by queueViewModel.currentSong.collectAsState()
    val currentPosition by queueViewModel.currentPosition.collectAsState()
    val duration by queueViewModel.duration.collectAsState()
    val volume by queueViewModel.volume.collectAsState()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface)
            .padding(16.dp)
    ) {
        // Song info
        currentSong?.let { song ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Song image (if available)
                song.image?.let { imageUrl ->
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = "Album art",
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = song.name,
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onSurface
                    )
                    song.artists?.get(0)?.let {
                        Text(
                            text = it.name,
                            style = MaterialTheme.typography.body2,
                            color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }

        // Progress bar
        Column {
            Slider(
                value = if (duration > 0) currentPosition.toFloat() / duration.toFloat() else 0f,
                onValueChange = { progress ->
                    if (duration > 0) {
                        val newPosition = (progress * duration).toLong()
                        queueViewModel.seekTo(newPosition)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = formatTime(currentPosition),
                    style = MaterialTheme.typography.caption
                )
                Text(
                    text = formatTime(duration),
                    style = MaterialTheme.typography.caption
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Control buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { queueViewModel.playPrevious() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Previous")
            }

            IconButton(
                onClick = { queueViewModel.playPause() },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = if (isPlaying) Icons.AutoMirrored.Filled.ArrowBack else Icons.Filled.PlayArrow,
                    contentDescription = if (isPlaying) "Pause" else "Play",
                    modifier = Modifier.size(32.dp)
                )
            }

            IconButton(onClick = { queueViewModel.playNext() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Next")
            }
        }

        // Volume control
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Filled.ThumbUp, contentDescription = "Volume")
            Slider(
                value = volume,
                onValueChange = { queueViewModel.setVolume(it) },
                modifier = Modifier.weight(1f)
            )
            Icon(Icons.Filled.ThumbUp, contentDescription = "Volume")
        }
    }
}

private fun formatTime(milliseconds: Long): String {
    val seconds = (milliseconds / 1000) % 60
    val minutes = (milliseconds / (1000 * 60)) % 60
    return secondsToMMSS((milliseconds * 1000).toInt())
}


@Composable
fun PlayScreen(song: Cancion, user: User) {
    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        // Background blur (puedes necesitar implementar un efecto de desenfoque personalizado)
        // en Android, puedes usar RenderEffect y en iOS, Core Image filters
        Image(
            painter = rememberAsyncImagePainter("https://img.freepik.com/vector-gratis/casa-encantadora-ilustracion-arbol_1308-176337.jpg"), // Reemplaza con tu imagen
            contentDescription = "Background Blur",
            modifier = Modifier.fillMaxSize().alpha(0.3f),
            contentScale = ContentScale.Crop
        )

        Column(modifier = Modifier.fillMaxSize()) {
            // Song info
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 48.dp, bottom = 24.dp, start = 24.dp, end = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    song.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                Text(song.id.toString(), fontSize = 14.sp, color = Color.Gray)
            }

            // Album art
            Box(
                modifier = Modifier.weight(1f).fillMaxWidth().padding(start = 40.dp, end = 40.dp),
                contentAlignment = Alignment.Center
            ) {
                var imageLoaded by rememberSaveable { mutableStateOf(false) }
                val painter = rememberAsyncImagePainter(
                    model = "https://ignsl.es/wp-content/uploads/2024/07/verifactu.jpg",
                    onError = { error ->
                        println("Error al cargar la imagen: ${error.result}")
                    }
                )
                Image(
                    painter = painter,
                    contentDescription = "Descripción de la imagen",
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Playback controls
            Column(modifier = Modifier.padding(start = 32.dp, end = 32.dp, bottom = 80.dp)) {
                // Progress bar
                Slider(
                    value = 38f,
                    onValueChange = {},
                    valueRange = 0f..100f,
                    modifier = Modifier.fillMaxWidth(),
                    colors = SliderDefaults.colors(
                        thumbColor = Color.White,
                        activeTrackColor = Color.White,
                        inactiveTrackColor = Color.White.copy(alpha = 0.3f)
                    )
                )

                // Time indicators
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("lo que lleve", fontSize = 12.sp, color = Color.Gray)
                    Text(secondsToMMSS(song.duration!!), fontSize = 12.sp, color = Color.Gray)
                }

                // Control buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {}) {
                        //ShuffleIcon(tint = Color.Gray)
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = {}) {
                            //SkipBackIcon(tint = Color.White)
                        }
                        IconButton(
                            onClick = {},
                            modifier = Modifier.size(56.dp)
                                .background(Color.White, shape = RoundedCornerShape(28.dp))
                        ) {
                            Icons.Filled.PlayArrow
                        }
                        IconButton(onClick = {}) {
                            //SkipForwardIcon(tint = Color.White)
                        }
                    }

                    IconButton(onClick = {}) {
                        //RepeatIcon(tint = Color.Gray)
                    }
                }
            }

            // Navigation bar
            Row(
                modifier = Modifier.fillMaxWidth()
                    .background(Color(0xFF282828))
                    .padding(vertical = 16.dp, horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                NavigationButton(icon = {
                    Icon(Icons.Filled.Home, "home")
                }, text = "Home")
                NavigationButton(icon = {
                    Icon(Icons.Filled.Search, "buscar")
                }, text = "Buscar")
                NavigationButton(icon = {
                    Icon(Icons.AutoMirrored.Filled.List, "Library")
                }, text = "Library")
                NavigationButton(icon = {
                    Icon(Icons.Filled.Person, "person")
                }, text = user.name)
            }
        }
    }
}

fun secondsToMMSS(length: Int): String {
    return "${(length / 60).toString().padStart(2, '0')}: ${
        (length % 60).toString().padStart(2, '0')
    }"
}

@Composable
fun NavigationButton(icon: @Composable () -> Unit, text: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        IconButton(onClick = {}) {
            icon()
        }
        Text(text, fontSize = 12.sp, color = Color.White)
    }
}