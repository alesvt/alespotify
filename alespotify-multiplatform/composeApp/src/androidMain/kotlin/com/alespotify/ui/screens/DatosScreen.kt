package com.alespotify.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.navigation.NavHostController
import coil3.Image
import coil3.compose.rememberAsyncImagePainter
import com.alespotify.ui.MyColors
import com.alespotify.ui.navigation.AppViewModel
import com.alespotify.ui.navigation.LoginViewModel
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.readBytes
import io.ktor.http.ContentType
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview


data class Playlist(val titulo: String, val desc: String)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
actual fun DatosScreen(
    navController: NavHostController,
    appViewModel: AppViewModel,
    loginViewModel: LoginViewModel
) {
    val isPlaying = remember { mutableStateOf(false) }
    val currentTab = remember { mutableStateOf("home") }
    val expandPlayer = remember { mutableStateOf(false) }
    val currentSlideIndex = remember { mutableStateOf(0) }
    val sliderState = rememberLazyListState()
    val featuredPlaylists = remember {
        listOf(
            Playlist("Weekly Discoveries", "Fresh music curated just for you"),
            Playlist("Summer Vibes", "Perfect playlist for sunny days"),
            Playlist("Chill Evening", "Relax with these smooth tracks")
        )
    }
    val coroutineScope = rememberCoroutineScope()

    // Function to handle tab changes
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
                // Mobile Bottom Navigation and Player
                Column(Modifier.background(MyColors.background)) {
                    // Mini Player / Expanded Player
                    if (!expandPlayer.value) {

                        MiniPlayer(
                            isPlaying = isPlaying.value,
                            onPlayPauseClick = { isPlaying.value = !isPlaying.value },
                            onExpandClick = { expandPlayer.value = true }
                        )
                    } else {
                        Player(
                            isPlaying = isPlaying.value,
                            onPlayPauseClick = { isPlaying.value = !isPlaying.value },
                            onCollapseClick = { expandPlayer.value = false },
                            appViewModel
                        )
                    }

                    // Tab Navigation
                    BottomNavigationBar(
                        currentTab = currentTab.value,
                        onTabSelected = onTabSelected
                    )
                }
            }
        ) {
            // Main Content Area
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(it)
                    .background(MyColors.background)
            ) {
                when (currentTab.value) {
                    "home" -> HomeScreen(
                        featuredPlaylists = featuredPlaylists,
                        currentSlideIndex = currentSlideIndex.value,
                        onSlideChange = { index ->
                            currentSlideIndex.value = index
                            coroutineScope.launch {
                                sliderState.animateScrollToItem(index)
                            }
                        },
                        sliderState = sliderState
                    )

                    "search" -> SearchScreen()
                    "library" -> LibraryScreen()
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
    sliderState: LazyListState
) {

    LazyRow(
        state = sliderState,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(featuredPlaylists.size) { index ->
            FeaturedPlaylistItem(
                playlist = featuredPlaylists[index],
                onItemClick = { onSlideChange(index) }
            )
        }
    }
    LaunchedEffect(currentSlideIndex) {
        sliderState.animateScrollToItem(currentSlideIndex)
    }

    Column(Modifier.verticalScroll(rememberScrollState())) {


        // Featured Carousel
        FeaturedCarousel(
            playlists = featuredPlaylists,
            currentSlideIndex = currentSlideIndex,
            onSlideChange = onSlideChange
        )

        // Recently Played
        RecentlyPlayedSection()

        // Made For You
        MadeForYouSection()

        // New Releases
        NewReleasesSection()

        Spacer(Modifier.padding(12.dp))
    }
}

@Composable
fun FeaturedCarousel(
    playlists: List<Playlist>,
    currentSlideIndex: Int,
    onSlideChange: (Int) -> Unit
) {
    val pageCount = playlists.size
    val pagerState = rememberPagerState(pageCount = { pageCount })


    LaunchedEffect(currentSlideIndex) {
        pagerState.animateScrollToPage(currentSlideIndex)
    }

    HorizontalPager(state = pagerState) { page ->
        FeaturedPlaylistItem(playlist = playlists[page], onItemClick = { onSlideChange(page) })
    }

    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pageCount) { iteration ->
            val color = if (pagerState.currentPage == iteration) MyColors.primary else Color.Gray
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

@Composable
fun FeaturedPlaylistItem(playlist: Playlist, onItemClick: () -> Unit) {
    Box(
        Modifier
            .height(200.dp)
            .fillMaxWidth()
            .clickable { onItemClick() }
    ) {
        Image(
            painter = rememberAsyncImagePainter("https://via.placeholder.com/400"),
            contentDescription = playlist.titulo,
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
            Text(playlist.titulo, style = MaterialTheme.typography.h5, color = Color.White)
            Text(
                playlist.desc,
                style = MaterialTheme.typography.body2,
                color = Color.White.copy(alpha = 0.8f)
            )
            Button(onClick = {}, modifier = Modifier.padding(top = 8.dp)) {
                Text("Play Now")
            }
        }
    }
}

@Composable
fun RecentlyPlayedSection() {
    Column(Modifier.padding(16.dp)) {
        Text("Recientes", style = MaterialTheme.typography.h6, color = Color.White)
        Spacer(modifier = Modifier.height(16.dp))
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            repeat(3) { index ->
                RecentlyPlayedItem(trackNumber = index + 1)
            }
        }
    }
}

@Composable
fun RecentlyPlayedItem(trackNumber: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /*TODO: Implement track click*/ }
            .padding(8.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter("https://via.placeholder.com/48"),
            contentDescription = "Track $trackNumber",
            modifier = Modifier.size(48.dp)
        )
        Column(Modifier.weight(1f)) {
            Text(
                "Track Title $trackNumber",
                style = MaterialTheme.typography.body1,
                color = Color.White
            )
            Text("Artist Name", style = MaterialTheme.typography.caption, color = Color.Gray)
        }
        IconButton(onClick = { /*TODO: Implement play icon click*/ }) {
            Icon(Icons.Filled.PlayArrow, contentDescription = "Play")
        }
    }
}

@Composable
fun MadeForYouSection() {
    Column(Modifier.padding(horizontal = 16.dp)) {
        Text("Para ti", style = MaterialTheme.typography.h6, color = Color.White)
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(6) { index ->
                MadeForYouItem(playlistNumber = index + 1)
            }
        }
    }
}

@Composable
fun MadeForYouItem(playlistNumber: Int) {
    Card(
        modifier = Modifier.size(120.dp)
    ) {
        Box(Modifier.fillMaxSize()) {
            Image(
                painter = rememberAsyncImagePainter("https://via.placeholder.com/150"),
                contentDescription = "Playlist $playlistNumber",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.align(Alignment.BottomEnd)
            ) {
                Icon(Icons.Filled.PlayArrow, contentDescription = "Play")
            }
        }
    }
}

@Composable
fun NewReleasesSection() {
    Column(Modifier.padding(horizontal = 16.dp)) {
        Text("Lo más nuevo", style = MaterialTheme.typography.h6, color = Color.White)
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(8) { index ->
                NewReleasesItem(albumNumber = index + 1)
            }
        }
    }
}

@Composable
fun NewReleasesItem(albumNumber: Int) {
    Card(
        modifier = Modifier.size(120.dp)
    ) {
        Box(Modifier.fillMaxSize()) {
            Image(
                painter = rememberAsyncImagePainter("https://via.placeholder.com/180"),
                contentDescription = "Album $albumNumber",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Column(Modifier.padding(8.dp)) {
            Text("New Album $albumNumber", style = MaterialTheme.typography.body1)
            Text("Artist Name", style = MaterialTheme.typography.caption, color = Color.Gray)
        }
    }
}

@Composable
fun SearchScreen() {
    Column(Modifier.padding(16.dp)) {
        Text("Search", style = MaterialTheme.typography.h5)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = "",
            onValueChange = {},
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
            placeholder = { Text("Artists, songs, or podcasts") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("Browse Categories", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(8.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(6) { index ->
                BrowseCategoryItem(
                    genre = listOf(
                        "Pop",
                        "Rock",
                        "Hip-Hop",
                        "Electronic",
                        "Jazz",
                        "Classical"
                    )[index]
                )
            }
        }
    }
}

@Composable
fun BrowseCategoryItem(genre: String) {
    Card(
        modifier = Modifier.height(120.dp)
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(
                        listOf(
                            Color.Red,
                            Color.Magenta
                        )
                    )
                ), // Placeholder gradient
            contentAlignment = Alignment.Center
        ) {
            Text(genre, style = MaterialTheme.typography.h6, color = Color.White)
        }
    }
}

@Composable
fun LibraryScreen() {
    Column(Modifier.padding(16.dp)) {
        Text("Your Library", style = MaterialTheme.typography.h5)
        Spacer(modifier = Modifier.height(16.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            repeat(5) { index ->
                LibraryItem(libraryItemNumber = index + 1)
            }
        }
    }
}

@Composable
fun LibraryItem(libraryItemNumber: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /*TODO*/ }
            .padding(8.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter("https://via.placeholder.com/56"),
            contentDescription = "Library Item $libraryItemNumber",
            modifier = Modifier.size(56.dp)
        )
        Column(Modifier.weight(1f)) {
            Text("My Playlist $libraryItemNumber", style = MaterialTheme.typography.body1)
            Text(
                if (libraryItemNumber % 2 == 0) "Playlist • ${10 + libraryItemNumber} songs" else "Album • ${10 + libraryItemNumber} songs",
                style = MaterialTheme.typography.caption,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun MiniPlayer(isPlaying: Boolean, onPlayPauseClick: () -> Unit, onExpandClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(MyColors.primary)
            .padding(8.dp)
            .clickable { onExpandClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = rememberAsyncImagePainter("https://upload.wikimedia.org/wikipedia/en/e/e6/The_Weeknd_-_Blinding_Lights.png"),
            contentDescription = "Now Playing",
            modifier = Modifier.size(40.dp)
        )
        Column(
            Modifier
                .weight(1f)
                .padding(start = 5.dp)
        ) {
            Text("Current Track", style = MaterialTheme.typography.body1, color = Color.White)
            Text(
                "Current Artist",
                style = MaterialTheme.typography.caption,
                color = MyColors.background
            )
        }
        IconButton(onClick = { onPlayPauseClick() }) {
            Icon(
                if (isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                contentDescription = "Play/Pause",
                tint = Color.White
            )
        }
    }
}

@Composable
fun AvatarWithFallback(fallbackText: String, imageUrl: String? = null) {
    if (imageUrl != null) {
        //TODO: replace with coil.compose.AsyncImage
        Image(
            painter = rememberAsyncImagePainter(imageUrl),
            contentDescription = "User Avatar",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )
    } else {

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
