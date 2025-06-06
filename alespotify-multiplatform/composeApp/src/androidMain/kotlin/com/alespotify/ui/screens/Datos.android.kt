package com.alespotify.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.rememberAsyncImagePainter
import com.alespotify.model.Cancion
import com.alespotify.model.Playlist
import com.alespotify.shared.ApiService
import com.alespotify.ui.MyColors
import com.alespotify.ui.navigation.AppViewModel
import com.alespotify.ui.navigation.LoginViewModel
import com.alespotify.ui.navigation.QueueViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
actual fun DatosScreen(
    navController: NavHostController,
    appViewModel: AppViewModel,
    loginViewModel: LoginViewModel,
    queueViewModel: QueueViewModel,
    apiService: ApiService
) {


    LaunchedEffect(Unit) {
        appViewModel.loadSongs()
    }

    val isPlaying = remember { mutableStateOf(false) }
    val currentTab = remember { mutableStateOf("home") }
    val expandPlayer = remember { mutableStateOf(false) }
    val currentSlideIndex = remember { mutableStateOf(0) }
    val sliderState = rememberLazyListState()
    val featuredPlaylists = appViewModel.playlists.collectAsState()
    val songs = appViewModel.songs.collectAsState()
    val artists = appViewModel.artists.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    val onTabSelected = { tab: String ->
        currentTab.value = tab
    }
    MaterialTheme(
        colors = MyColors
    ) {

        Scaffold(
            Modifier
                .background(Color.Black),
            bottomBar = {

                Column(Modifier.background(MyColors.background)) {

                    if (!expandPlayer.value) {
                        MiniPlayer(
                            isPlaying = isPlaying.value,
                            onPlayPauseClick = { isPlaying.value = !isPlaying.value },
                            onExpandClick = { expandPlayer.value = true },
                            queueViewModel
                        )
                    } else {
                        Player(
                            isPlaying = isPlaying.value,
                            onPlayPauseClick = { isPlaying.value = !isPlaying.value },
                            onCollapseClick = { expandPlayer.value = false },
                            appViewModel,
                            queueViewModel
                        )
                    }


                    BottomNavigationBar(
                        currentTab = currentTab.value,
                        onTabSelected = onTabSelected
                    )
                }
            }
        ) {

            Box(
                Modifier
                    .fillMaxSize()
                    .padding(it)
                    .background(MyColors.background)
            ) {
                when (currentTab.value) {
                    "home" -> featuredPlaylists.value?.let { it1 ->
                        HomeScreen(
                            featuredPlaylists = it1,
                            currentSlideIndex = currentSlideIndex.value,
                            onSlideChange = { index ->
                                currentSlideIndex.value = index
                                coroutineScope.launch {
                                    sliderState.animateScrollToItem(index)
                                }
                            },
                            sliderState = sliderState,
                            queueViewModel = queueViewModel,
                            songs = songs.value,
                            onPlaylistClick = {}
                        )
                    }

                    "search" -> SearchScreen(queueViewModel)
                    "library" -> LibraryScreen(featuredPlaylists.value, queueViewModel)
                }
            }
        }
    }
}

@Composable
fun HomeScreen(
    featuredPlaylists: List<Playlist>,
    currentSlideIndex: Int,
    onSlideChange: (Int) -> Unit,
    songs: List<Cancion>?,
    queueViewModel: QueueViewModel,
    sliderState: LazyListState,
    onPlaylistClick: () -> Unit
) {


    LaunchedEffect(currentSlideIndex) {
        sliderState.animateScrollToItem(currentSlideIndex)
    }

    Column(Modifier.verticalScroll(rememberScrollState())) {

        FeaturedCarousel(
            playlists = featuredPlaylists,
            currentSlideIndex = currentSlideIndex,
            onSlideChange = onSlideChange,
            onPlaylistClick = onPlaylistClick
        )


        println(songs)
        if (songs != null) {
            RecentlyPlayedSection(songs = songs, queueViewModel = queueViewModel)
        }

        MadeForYouSection(playlists = featuredPlaylists, queueViewModel)

        Spacer(Modifier.padding(12.dp))
    }
}

@Composable
fun FeaturedCarousel(
    playlists: List<Playlist>,
    currentSlideIndex: Int,
    onSlideChange: (Int) -> Unit,
    onPlaylistClick: () -> Unit
) {
    val pageCount = playlists.size
    var pagerState = rememberPagerState { 3 }
    pagerState = rememberPagerState(pageCount = { pageCount })


    LaunchedEffect(currentSlideIndex) {
        pagerState.animateScrollToPage(currentSlideIndex)
    }

    HorizontalPager(state = pagerState) { page ->
        val pl = playlists.get(page)

        FeaturedPlaylistItem(
            playlist = pl,
            onItemClick = { onSlideChange(page) },
            onPlaylistClick = {})

    }

    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        if (pageCount != null) {
            repeat(pageCount) { iteration ->
                val color =
                    if (pagerState.currentPage == iteration) MyColors.primary else Color.Gray
                Box(
                    modifier = Modifier
                        .padding(3.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(10.dp)
                )
            }
        }
    }

}

@Composable
fun FeaturedPlaylistItem(playlist: Playlist, onItemClick: () -> Unit, onPlaylistClick: () -> Unit) {
    Box(
        Modifier
            .height(200.dp)
            .fillMaxWidth()
            .clickable { onItemClick() }
    ) {
        Image(
            painter = rememberAsyncImagePainter(playlist.image),
            contentDescription = playlist.nombre,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.6f))
        )
        Column(
            Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Text(playlist.nombre, style = MaterialTheme.typography.h5, color = Color.White)
            Button(onClick = { onPlaylistClick() }, modifier = Modifier.padding(top = 8.dp)) {
                Text("Reproducir")
            }
        }
    }
}

@Composable
fun RecentlyPlayedSection(songs: List<Cancion>, queueViewModel: QueueViewModel) {
    Column(Modifier.padding(16.dp)) {
        Text("Lo más nuevo", style = MaterialTheme.typography.h6, color = Color.White)
        Spacer(modifier = Modifier.height(16.dp))
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            songs.forEach { s ->
                RecentlyPlayedItem(s, queueViewModel)
            }
        }
    }
}

@Composable
fun RecentlyPlayedItem(song: Cancion, queueViewModel: QueueViewModel) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { queueViewModel.playSong(song) }
            .padding(8.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(song.image),
            contentDescription = "Track ${song.id}",
            modifier = Modifier.size(48.dp)
        )
        Column(Modifier.weight(1f)) {
            Text(
                song.name,
                style = MaterialTheme.typography.body1,
                color = Color.White
            )
            song.artists?.get(0)?.let {
                Text(
                    it.name,
                    style = MaterialTheme.typography.caption,
                    color = Color.Gray
                )
            }
        }
        IconButton(onClick = { queueViewModel.playSong(song) }) {
            Icon(Icons.Filled.PlayArrow, contentDescription = "Play")
        }
    }
}

@Composable
fun MadeForYouSection(playlists: List<Playlist>, queueViewModel: QueueViewModel) {
    Column(Modifier.padding(horizontal = 16.dp)) {
        Text("Para ti", style = MaterialTheme.typography.h6, color = Color.White)
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(playlists) { pl ->
                MadeForYouItem(pl, queueViewModel = queueViewModel)
            }
        }
    }
}

@Composable
fun MadeForYouItem(playlist: Playlist, queueViewModel: QueueViewModel) {
    Card(
        modifier = Modifier.size(120.dp)
    ) {
        Box(Modifier.fillMaxSize()) {
            Image(
                painter = rememberAsyncImagePainter(playlist.image),
                contentDescription = "Playlist ${playlist.nombre}",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            IconButton(
                onClick = { queueViewModel.playPlaylist(playlist) },
                modifier = Modifier.align(Alignment.BottomEnd)
            ) {
                Icon(Icons.Filled.PlayArrow, contentDescription = "Play")
            }
        }
    }
}


@Composable
fun SearchScreen(queueViewModel: QueueViewModel) {
    var queryBusqueda by remember { mutableStateOf("") }
    var resultadosBusqueda by remember { mutableStateOf<List<Cancion>?>(null) }
    val scope = rememberCoroutineScope()
    var searchJob: Job? by remember { mutableStateOf(null) }
    val apiService = ApiService()

    Column(Modifier.padding(16.dp, top = 32.dp)) {
        Text("Búsqueda", style = MaterialTheme.typography.h5)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = queryBusqueda,
            onValueChange = { texto ->
                queryBusqueda = texto
                searchJob?.cancel()
                searchJob = scope.launch {
                    delay(300)
                    if (texto.isNotBlank()) {
                        try {
                            resultadosBusqueda = apiService.getSongsByName(texto)
                        } catch (e: Exception) {
                            resultadosBusqueda = emptyList()
                        }
                    } else {
                        resultadosBusqueda = emptyList()
                    }
                }

            },
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
            placeholder = { Text("Artists, songs, or podcasts") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))
        if (resultadosBusqueda?.isEmpty() == true && queryBusqueda.isNotBlank()) {
            Text(
                "No hay resultados",
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
                modifier = Modifier.padding(vertical = 16.dp)
            )
        } else if (resultadosBusqueda?.isEmpty() == true && queryBusqueda.isBlank()) {
            Text(
                text = "Empieza a escribir para buscar canciones...",
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
                modifier = Modifier.padding(vertical = 16.dp)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(resultadosBusqueda ?: emptyList()) { cancion ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                scope.launch {
                                    queueViewModel.playSong(cancion)
                                }
                            }
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = cancion.name,
                                color = MaterialTheme.colors.onSurface,
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp
                            )
                            cancion.artists?.let { artist ->
                                Text(
                                    text = artist[0].name,
                                    color = MyColors.secondary.copy(alpha = 0.6f),
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                    Divider(color = MyColors.secondary.copy(alpha = 0.1f))
                }

            }
        }
    }
}


@Composable
fun LibraryScreen(playlists: List<Playlist>?, queueViewModel: QueueViewModel) {
    Column(Modifier.padding(16.dp, top = 32.dp)) {
        Text("Tus playlists", style = MaterialTheme.typography.h5)
        Spacer(modifier = Modifier.height(16.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            playlists?.forEach { p ->
                LibraryItem(p, queueViewModel = queueViewModel)
            }
        }
    }
}

@Composable
fun LibraryItem(playlist: Playlist, queueViewModel: QueueViewModel) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { queueViewModel.playPlaylist(playlist) }
            .padding(8.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(playlist.image),
            contentDescription = "Playlist image",
            modifier = Modifier.size(56.dp)
        )
        Column(Modifier.weight(1f)) {
            Text(playlist.nombre, style = MaterialTheme.typography.body1)
            Text(
                "${playlist.songs?.size} canciones · By ${playlist.user.name}",
                style = MaterialTheme.typography.caption,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun MiniPlayer(
    isPlaying: Boolean,
    onPlayPauseClick: () -> Unit,
    onExpandClick: () -> Unit,
    queueViewModel: QueueViewModel
) {
    val currentSong by queueViewModel.currentSong.collectAsState()
    val isPlayingState by queueViewModel.isPlaying.collectAsState()

    Row(
        Modifier
            .fillMaxWidth()
            .background(MyColors.primary)
            .padding(8.dp)
            .clickable { onExpandClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                currentSong?.image ?: "https://via.placeholder.com/40"
            ),
            contentDescription = "Now Playing",
            modifier = Modifier.size(40.dp)
        )
        Column(
            Modifier
                .weight(1f)
                .padding(start = 5.dp)
        ) {
            Text(
                currentSong?.name ?: "Sin canción",
                style = MaterialTheme.typography.body1,
                color = Color.White
            )
            Text(
                currentSong?.artists?.firstOrNull()?.name ?: "Artista desconocido",
                style = MaterialTheme.typography.caption,
                color = MyColors.background
            )
        }
        IconButton(onClick = { queueViewModel.playPause() }) {
            Icon(
                if (isPlayingState) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                contentDescription = "Play/Pause",
                tint = Color.White
            )
        }
    }
}


@Composable
fun BottomNavigationBar(currentTab: String, onTabSelected: (String) -> Unit) {
    NavigationBar(

        containerColor = Color.Transparent,

        ) {
        NavigationBarItem(
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                indicatorColor = Color(0xff3b3b3b),
            ),
            selected = currentTab == "home",
            onClick = { onTabSelected("home") },
            icon = {
                Icon(Icons.Filled.Home, contentDescription = "Home", tint = Color.White)
            },
        )
        NavigationBarItem(
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                indicatorColor = Color(0xff3b3b3b)
            ),
            selected = currentTab == "search",
            onClick = { onTabSelected("search") },
            icon = {
                Icon(
                    Icons.Filled.Search,
                    contentDescription = "Search", tint = Color.White
                )
            },
        )
        NavigationBarItem(
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                indicatorColor = Color(0xff3b3b3b)
            ),
            selected = currentTab == "library",
            onClick = { onTabSelected("library") },
            icon = {
                Icon(
                    Icons.Filled.LibraryMusic,
                    contentDescription = "Library", tint = Color.White
                )
            },
        )
    }
}
