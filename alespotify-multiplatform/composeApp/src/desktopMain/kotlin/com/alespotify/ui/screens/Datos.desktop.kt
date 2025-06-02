package com.alespotify.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.Window
import androidx.navigation.NavHostController
import com.alespotify.model.Cancion
import com.alespotify.model.Playlist
import com.alespotify.model.User
import com.alespotify.shared.ApiService
import com.alespotify.ui.MyColors
import com.alespotify.ui.navigation.AppViewModel
import com.alespotify.ui.navigation.LoginViewModel
import com.alespotify.ui.navigation.QueueViewModel

import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.system.exitProcess


fun getHoraDia(): String {
    val formatter = DateTimeFormatter.ofPattern("HH")
    val horaActual = LocalTime.now()
    when {
        horaActual.isBefore(LocalTime.parse("14", formatter)) -> return "Buenos días"
        horaActual.isAfter(LocalTime.parse("14", formatter)) -> return "Buenas tardes"
        horaActual.isAfter(LocalTime.parse("20", formatter)) -> return "Buenas noches"
    }
    return ""
}

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

    val songs by appViewModel.songs.collectAsState()
    val playlists by appViewModel.playlists.collectAsState()
    val artists by appViewModel.artists.collectAsState()
    val user by loginViewModel.usuario.collectAsState()

    println("Artists loaded: ${artists?.size}")
    println("Songs loaded: ${songs?.size}")
    println("Playlists loaded: ${playlists?.size}")


    var dialogobusqueda by remember { mutableStateOf(false) }

    val textoBienvenida = getHoraDia()


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
                    NavigationLink(
                        icon = Icons.Filled.Home,
                        text = "Home",
                        selected = true,
                        onClick = {})
                    NavigationLink(icon = Icons.Filled.Search, text = "Búsqueda", onClick = {dialogobusqueda = true})

                }

                Divider(modifier = Modifier.padding(vertical = 16.dp))

                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Tus Playlists",
                        fontSize = 14.sp,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    NavigationLink(icon = Icons.Filled.Favorite, text = "Favoritos", onClick = {})
                    NavigationLink(
                        icon = Icons.Filled.History,
                        text = "Canciones recientes",
                        onClick = {})
                    for (playlist in playlists ?: emptyList()) {
                        NavigationLink(
                            icon = Icons.Filled.LibraryMusic,
                            text = playlist.nombre,
                            onClick = {
                                println("se ha clicado")
                                queueViewModel.playPlaylist(playlist)
                            })
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                Column(modifier = Modifier.padding(16.dp)) {
                    NavigationLink(icon = Icons.Filled.AccountBox, text = "Perfil", onClick = {})
                    NavigationLink(
                        icon = Icons.AutoMirrored.Filled.ExitToApp,
                        text = "Salir",
                        onClick = { exitProcess(0) })
                }
            }

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
                            text = textoBienvenida,
                            fontSize = 24.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        user?.let { currentUser ->
                            currentUser.imagen?.let { Avatar(currentUser.name, it) }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {

                        FeaturedCard(
                            "Descubrimiento semanal",
                            "Esta semana te traemos...",
                            "https://publitronic.es/wp-content/uploads/2025/05/Diseno-sin-titulo2.png",
                        )

                    }
                    Text(
                        text = "Los artistas más top",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        artists?.let {
                            items(it) { index ->
                                TopArtistsCard(index)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Para ti",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        playlists?.let {
                            items(it) { index ->
                                MadeForYouCard(index, queueViewModel)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Lo más nuevo",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        songs?.let {
                            items(it) { s ->
                                NewReleaseCard(s, queueViewModel = queueViewModel)
                            }
                        }

                    }
                }

                // Player
                PlayerControls(
                    queueViewModel = queueViewModel
                )
            }
        }
        if (dialogobusqueda) {
            DialogoBuscaCanciones(
                apiService,
                queueViewModel,
                onDismiss = { dialogobusqueda = false }
            )
        }
    }
}

