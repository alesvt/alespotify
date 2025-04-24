package com.alespotify.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.alespotify.ui.navigation.AppViewModel
import com.alespotify.ui.navigation.DestinosNavegacion
import kotlinx.coroutines.launch

@Composable
fun LoadingDataScreen(navController: NavHostController, appViewModel: AppViewModel) {
    val isLoadingSongs by appViewModel.isLoadingSongs.collectAsState()
    val isLoadingArtists by appViewModel.isLoadingArtists.collectAsState()
    val isLoadingPlaylists by appViewModel.isLoadingPlaylists.collectAsState()

    val songsLoaded by appViewModel.songsLoaded.collectAsState()
    val artistsLoaded by appViewModel.artistsLoaded.collectAsState()
    val playlistsLoaded by appViewModel.playlistsLoaded.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
        Text("canciones $isLoadingSongs")
        Text("artistas $isLoadingArtists")
        Text("playlists $isLoadingPlaylists")
        Text(text = "Cargando datos...")
    }

    // Navegar a DatosScreen cuando todas las listas estén cargadas
    if (songsLoaded && artistsLoaded && playlistsLoaded) {
        LaunchedEffect(key1 = Unit) {
            navController.navigate(DestinosNavegacion.app.route) {
                popUpTo(DestinosNavegacion.load.route) { inclusive = true } // Evita volver
            }
        }
    }
}