package com.alespotify.ui.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.alespotify.model.Cancion
import com.alespotify.model.User
import com.alespotify.ui.MyColors
import com.alespotify.ui.navigation.AppViewModel
import com.alespotify.ui.navigation.LoginViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Brush
import coil3.compose.AsyncImage
import com.alespotify.model.Artist
import com.alespotify.model.Playlist
import com.alespotify.shared.ApiService
import com.alespotify.ui.navigation.QueueViewModel
import com.alespotify.ui.navigation.RepeatMode


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
fun NavigationLink(icon: ImageVector, text: String, selected: Boolean = false, onClick: () -> Unit) {
    val backgroundColor =
        if (selected) MaterialTheme.colors.primary.copy(alpha = 0.1f) else Color.Transparent
    val textColor =
        if (selected) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface.copy(alpha = 0.8f)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
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
fun MadeForYouCard(playlist: Playlist, queueViewModel: QueueViewModel) {
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
                onClick = { queueViewModel.playPlaylist(playlist) },
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

        currentSong?.let { song ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                AsyncImage(
                    model = song.image,
                    contentDescription = "Imagen cancion",
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = song.name,
                        style = MaterialTheme.typography.h6,
                        color = MaterialTheme.colors.onSurface,
                        maxLines = 1
                    )
                    song.artists?.firstOrNull()?.let { artist ->
                        Text(
                            text = artist.name,
                            style = MaterialTheme.typography.body2,
                            color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f),
                            maxLines = 1
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }


        Column {
            Slider(
                value = if (duration > 0) currentPosition.toFloat() / duration.toFloat() else 0f,
                onValueChange = { progress ->
                    if (duration > 0) {
                        val newPosition = (progress * duration).toLong()
                        queueViewModel.seekTo(newPosition)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = SliderDefaults.colors(
                    thumbColor = MaterialTheme.colors.primary,
                    activeTrackColor = MaterialTheme.colors.primary,
                    inactiveTrackColor = MaterialTheme.colors.onSurface.copy(alpha = 0.3f)
                )
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = formatTime(currentPosition),
                    style = MaterialTheme.typography.caption,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                )
                Text(
                    text = formatTime(duration),
                    style = MaterialTheme.typography.caption,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { queueViewModel.toggleShuffle() }
            ) {
                Icon(
                    Icons.Filled.Shuffle,
                    contentDescription = "Randomizar",
                    tint = if (queueViewModel.isShuffleEnabled.collectAsState().value)
                        MaterialTheme.colors.primary else MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                )
            }

            IconButton(onClick = { queueViewModel.playPrevious() }) {
                Icon(
                    Icons.Filled.SkipPrevious,
                    contentDescription = "Previous",
                    tint = MaterialTheme.colors.onSurface
                )
            }

            IconButton(
                onClick = { queueViewModel.playPause() },
                modifier = Modifier.size(64.dp)
            ) {
                Icon(
                    imageVector = if (isPlaying) Icons.Filled.PauseCircleFilled else Icons.Filled.PlayCircleFilled,
                    contentDescription = if (isPlaying) "Pause" else "Play",
                    modifier = Modifier.size(64.dp),
                    tint = MaterialTheme.colors.primary
                )
            }

            IconButton(onClick = { queueViewModel.playNext() }) {
                Icon(
                    Icons.Filled.SkipNext,
                    contentDescription = "Next",
                    tint = MaterialTheme.colors.onSurface
                )
            }

            IconButton(onClick = { queueViewModel.toggleRepeat() }) {
                val repeatMode by queueViewModel.repeatMode.collectAsState()
                Icon(
                    when (repeatMode) {
                        RepeatMode.ONE -> Icons.Filled.RepeatOne
                        else -> Icons.Filled.Repeat
                    },
                    contentDescription = "Repetir",
                    tint = when (repeatMode) {
                        RepeatMode.ALL -> MaterialTheme.colors.primary
                        RepeatMode.ONE -> MaterialTheme.colors.primary
                        else -> MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.AutoMirrored.Filled.VolumeMute,
                contentDescription = "Mute",
                tint = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
            )
            Slider(
                value = volume,
                onValueChange = { queueViewModel.setVolume(it) },
                modifier = Modifier.weight(1f),
                colors = SliderDefaults.colors(
                    thumbColor = MaterialTheme.colors.primary,
                    activeTrackColor = MaterialTheme.colors.primary,
                    inactiveTrackColor = MaterialTheme.colors.onSurface.copy(alpha = 0.3f)
                )
            )
            Icon(
                Icons.AutoMirrored.Filled.VolumeUp,
                contentDescription = "Volume Up",
                tint = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}


fun formatTime(milliseconds: Long): String {
    val totalSeconds = milliseconds / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return "${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}"
}
