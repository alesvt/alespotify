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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.alespotify.ui.navigation.AppViewModel
import com.alespotify.ui.navigation.DestinosNavegacion
import com.alespotify.ui.navigation.LoginViewModel
import kotlinx.coroutines.launch

@Composable
fun LoadingDataScreen(
    navController: NavHostController,
    appViewModel: AppViewModel = AppViewModel(),
    loginViewModel: LoginViewModel = LoginViewModel()
) {
    val isLoadingSongs by appViewModel.isLoadingSongs.collectAsState()
    val isLoadingArtists by appViewModel.isLoadingArtists.collectAsState()
    val isLoadingPlaylists by appViewModel.isLoadingPlaylists.collectAsState()

    val songsLoaded by appViewModel.songsLoaded.collectAsState()
    val artistsLoaded by appViewModel.artistsLoaded.collectAsState()
    val playlistsLoaded by appViewModel.playlistsLoaded.collectAsState()

    val errorMessage by appViewModel.errorMessage.collectAsState()


    val songesteit by appViewModel.songs.collectAsState()
    println(songesteit)

    println("cargan?")
    println(isLoadingSongs)
    println(songsLoaded)
    println(artistsLoaded)
    // Lanzar la carga de datos al entrar a la pantalla
    LaunchedEffect(key1 = Unit) {
        // Asegurarse de que la carga se inicie
        if (!songsLoaded && !artistsLoaded && !playlistsLoaded) {
            appViewModel.loadSongs()
            println(loginViewModel.nose.value)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
        Text("Cargando canciones: ${if (isLoadingSongs) "Sí" else "No"}")
        Text("Cargando artistas: ${if (isLoadingArtists) "Sí" else "No"}")
        Text("Cargando playlists: ${if (isLoadingPlaylists) "Sí" else "No"}")

        Text(text = errorMessage, color = MaterialTheme.colors.error)

        // Agregar botón para reintento manual en caso de fallo
        if (!isLoadingSongs && !isLoadingArtists && !isLoadingPlaylists &&
            (!songsLoaded || !artistsLoaded || !playlistsLoaded)
        ) {
            Button(onClick = { appViewModel.loadSongs() }) {
                Text("Reintentar carga")
            }
        }
    }

    // Navegar cuando todas las listas estén cargadas o al menos las necesarias
    if ((songsLoaded || !appViewModel.songs.value.isNullOrEmpty()) &&
        (artistsLoaded || !appViewModel.artists.value.isNullOrEmpty()) &&
        (playlistsLoaded || !appViewModel.playlists.value.isNullOrEmpty())
    ) {
        LaunchedEffect(key1 = Unit) {
            navController.navigate(DestinosNavegacion.App.route) {
                println("ya están cargadas las listas, nos vamos")
            }
        }
    }
}