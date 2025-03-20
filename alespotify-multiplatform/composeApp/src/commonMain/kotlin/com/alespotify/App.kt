package com.alespotify

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import com.alespotify.model.Cancion
import com.alespotify.ui.ApiClient
import com.alespotify.ui.ListaCanciones
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@Composable

fun App() {
    val apiClient = ApiClient()
    var canciones by remember { mutableStateOf<List<Cancion>>(emptyList()) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            canciones = apiClient.obtenerCanciones()

        }
    }

    MaterialTheme {
        Surface {
            ListaCanciones(canciones)
        }
    }
}