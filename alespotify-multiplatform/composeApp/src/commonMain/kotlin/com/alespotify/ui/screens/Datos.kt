package com.alespotify.ui.screens

/*
import alespotify_multiplatform.composeapp.generated.resources.Res
import alespotify_multiplatform.composeapp.generated.resources.allStringResources
import alespotify_multiplatform.composeapp.generated.resources.compose_multiplatform
*/
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
import androidx.compose.material.*
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
import androidx.compose.material.icons.automirrored.filled.*
import coil3.compose.rememberAsyncImagePainter

import org.jetbrains.compose.resources.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.alespotify.model.Cancion
import com.alespotify.model.User
import com.alespotify.ui.MyColors
import com.alespotify.ui.navigation.AppViewModel
import com.alespotify.ui.navigation.ArtistDataState
import com.alespotify.ui.navigation.DestinosNavegacion
import com.alespotify.ui.navigation.LoginState
import com.alespotify.ui.navigation.LoginViewModel
import com.alespotify.ui.navigation.PlaylistDataState
import com.alespotify.ui.navigation.SongDataState
import kotlinx.coroutines.flow.StateFlow
import kotlin.math.log

expect fun getPlatformName(): String

@Composable
fun MainView() {
    val platformName = getPlatformName()
    val navController = rememberNavController()
    Button(onClick = {
        if (platformName == "Android") {
            println("Jamon de york")
            navController.navigate(DestinosNavegacion.android.route)
        } else {
            navController.navigate("ios")
        }
    }) {
        Text("Navegar a la vista especifica")
    }
}


@OptIn(ExperimentalResourceApi::class)
@Composable
fun DatosScreen(
    navController: NavHostController,
    appViewModel: AppViewModel,
    loginViewModel: LoginViewModel

) {

    var sliderValue by remember { mutableStateOf(33f) }
    var isPlaying by remember { mutableStateOf(false) }
    var volumeSliderValue by remember { mutableStateOf(80f) }

    val songDataState by appViewModel.songDataState
    val artistDataState by appViewModel.artistDataState
    val playlistDataState by appViewModel.playlistDataState

    // Obtener los datos del usuario desde LoginViewModel
    val user by loginViewModel.loginState

    var usuario  = loginViewModel.loginState.value
    // Comprobar si los datos están siendo cargados, si es necesario, se recargan
    LaunchedEffect(Unit) {
        if (songDataState is SongDataState.Loading || artistDataState is ArtistDataState.Loading || playlistDataState is PlaylistDataState.Loading) {
            appViewModel.loadSongs()
        }
        if (user is LoginState.Success) run {
            usuario = (user as LoginState.Success).user
        } else {

        }
    }

    MaterialTheme(colors = MyColors) {
        Row(modifier = Modifier.fillMaxSize()) {
            // Sidebar
            Column(
                modifier = Modifier
                    .width(256.dp)
                    .fillMaxHeight()
                    .background(MaterialTheme.colors.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Alespotify",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.primary,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    NavigationLink(icon = Icons.Filled.Home, text = "Home", selected = true)
                    NavigationLink(icon = Icons.Filled.Search, text = "Search")
                    NavigationLink(icon = Icons.Filled.LibraryMusic, text = "Tu música")
                }

                Divider(modifier = Modifier.padding(vertical = 16.dp))

                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Tus Playlists",
                        fontSize = 14.sp,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    NavigationLink(icon = Icons.Filled.Favorite, text = "Favoritos")
                    NavigationLink(icon = Icons.Filled.History, text = "Canciones recientes")
                }

                Spacer(modifier = Modifier.weight(1f))

                Column(modifier = Modifier.padding(16.dp)) {
                    print(usuario)
                    NavigationLink(icon = Icons.Filled.AccountBox, text = "Perfil")
                    NavigationLink(icon = Icons.Filled.Settings, text = "Ajustes")
                }
            }

            // Main Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .background(MaterialTheme.colors.background)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Good afternoon",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Avatar("JD", "placeholder.svg")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        FeaturedCard(
                            "Weekly Discoveries",
                            "Fresh music curated just for you",
                            "placeholder.svg"
                        )
                        Column(modifier = Modifier.weight(1f)) {
                            RecentlyPlayedCard()
                            Spacer(modifier = Modifier.height(16.dp))
                            TopArtistsCard()
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Made For You",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        items(List(6) { it + 1 }) { index ->
                            MadeForYouCard(index)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "New Releases",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        items(List(2) { it + 1 }) { index ->
                            NewReleaseCard(index)
                        }
                    }
                }

                // Player
                PlayerControls(
                    isPlaying = isPlaying,
                    onPlayPauseToggle = { isPlaying = !isPlaying },
                    sliderValue = sliderValue,
                    onSliderValueChange = { sliderValue = it },
                    volumeSliderValue = volumeSliderValue,
                    onVolumeSliderValueChange = { volumeSliderValue = it }
                )
            }
        }
    }
}

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
            painter = rememberAsyncImagePainter("https://cdn-images.dzcdn.net/images/cover/b159f9470a45ca0ecda42062136ac33a/0x1900-000000-80-0-0.jpg"),
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
                    Text("Play Now")
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
fun TopArtistsCard() {
    Card {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Your Top Artists",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                List(3) { it + 1 }.forEach { index ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            rememberAsyncImagePainter("https://cdn-images.dzcdn.net/images/cover/b159f9470a45ca0ecda42062136ac33a/0x1900-000000-80-0-0.jpg"),
                            contentDescription = "Artist $index",
                            modifier = Modifier.size(64.dp).clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        Text(
                            text = "Artist $index",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun MadeForYouCard(index: Int) {
    Card {
        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                rememberAsyncImagePainter("https://cdn-images.dzcdn.net/images/cover/b159f9470a45ca0ecda42062136ac33a/0x1900-000000-80-0-0.jpg"),
                contentDescription = "Playlist $index",
                modifier = Modifier.aspectRatio(1f),
                contentScale = ContentScale.Crop
            )
            IconButton(onClick = { }, modifier = Modifier.align(Alignment.BottomEnd)) {
                Icon(imageVector = Icons.Filled.PlayArrow, contentDescription = "Play")
            }
        }
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = "Playlist $index", fontWeight = FontWeight.Medium)
            Text(
                text = "By Melodify",
                fontSize = 12.sp,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun NewReleaseCard(index: Int) {
    Card(modifier = Modifier.width(180.dp)) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                rememberAsyncImagePainter("https://cdn-images.dzcdn.net/images/cover/b159f9470a45ca0ecda42062136ac33a/0x1900-000000-80-0-0.jpg"),
                contentDescription = "New Release $index",
                modifier = Modifier.aspectRatio(1f),
                contentScale = ContentScale.Crop
            )
        }
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = "New Album $index", fontWeight = FontWeight.Medium)
            Text(
                text = "Artist Name",
                fontSize = 12.sp,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PlayerControls(
    isPlaying: Boolean,
    onPlayPauseToggle: () -> Unit,
    sliderValue: Float,
    onSliderValueChange: (Float) -> Unit,
    volumeSliderValue: Float,
    onVolumeSliderValueChange: (Float) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Image(
                    rememberAsyncImagePainter("https://cdn-images.dzcdn.net/images/cover/b159f9470a45ca0ecda42062136ac33a/0x1900-000000-80-0-0.jpg"),
                    contentDescription = "Now Playing",
                    modifier = Modifier.size(56.dp).clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Crop
                )
                Column {
                    Text(text = "Current Track", fontWeight = FontWeight.Medium)
                    Text(
                        text = "Current Artist",
                        fontSize = 12.sp,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                    )
                }
                IconButton(onClick = { }) {
                    Icon(imageVector = Icons.Filled.Favorite, contentDescription = "Like")
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Filled.Shuffle,
                        contentDescription = "Shuffle"
                    )
                }
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Skip Back"
                    )
                }
                IconButton(onClick = onPlayPauseToggle) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                        contentDescription = "Play/Pause"
                    )
                }
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Skip Forward"
                    )
                }
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Filled.Repeat,
                        contentDescription = "Repeat"
                    )
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                    contentDescription = "Volume"
                )
                Slider(value = volumeSliderValue, onValueChange = onVolumeSliderValueChange)
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "1:23",
                fontSize = 12.sp,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
            )
            Slider(
                value = sliderValue,
                onValueChange = onSliderValueChange,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "3:45",
                fontSize = 12.sp,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
            )
        }
    }
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
                    song.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                Text(song.id, fontSize = 14.sp, color = Color.Gray)
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
                    Text(secondsToMMSS(song.length!!), fontSize = 12.sp, color = Color.Gray)
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