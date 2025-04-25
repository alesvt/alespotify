package com.alespotify.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.alespotify.ui.navigation.AppViewModel
import com.alespotify.ui.navigation.ArtistDataState
import com.alespotify.ui.navigation.DestinosNavegacion
import com.alespotify.ui.navigation.LoginViewModel
import com.alespotify.ui.navigation.PlaylistDataState
import com.alespotify.ui.navigation.SongDataState
import kotlinx.coroutines.launch

@Composable
fun LoadingDataScreen(
    navController: NavHostController,
    appViewModel: AppViewModel,
    loginViewModel: LoginViewModel
) {
    val songDataState by appViewModel.songDataState
    val artistDataState by appViewModel.artistDataState
    val playlistDataState by appViewModel.playlistDataState

    val user by loginViewModel.loginState
    // Lanzar la carga de datos al entrar a la pantalla
    LaunchedEffect(Unit) {
        if (songDataState is SongDataState.Loading || artistDataState is ArtistDataState.Loading || playlistDataState is PlaylistDataState.Loading) {
            appViewModel.loadSongs()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()

        Text("Cargando canciones: ${if (songDataState is SongDataState.Loading) "Sí" else "No"}")
        Text("Cargando artistas: ${if (artistDataState is ArtistDataState.Loading) "Sí" else "No"}")
        Text("Cargando playlists: ${if (playlistDataState is PlaylistDataState.Loading) "Sí" else "No"}")



    }

    if (songDataState is SongDataState.Error || artistDataState is ArtistDataState.Error || playlistDataState is PlaylistDataState.Error) {
        Button(onClick = {
            appViewModel.loadSongs()
        }) {
            Text("Reintentar")
        }
    }

    LaunchedEffect(key1 = songDataState, key2 = artistDataState, key3 = playlistDataState) {
        if (songDataState !is SongDataState.Loading &&
            artistDataState !is ArtistDataState.Loading &&
            playlistDataState !is PlaylistDataState.Loading
        ) {
            navController.navigate(DestinosNavegacion.app.route) {
                // Eliminar la pantalla de carga de la pila de navegación
                popUpTo(DestinosNavegacion.load.route) { inclusive = true }
            }
        }
    }
}