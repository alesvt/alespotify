package com.alespotify.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import com.alespotify.model.Cancion
import kotlinx.coroutines.launch
// import COIL

@Composable
fun LlamadaApi() {
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

@Composable
fun ListaCanciones(canciones: List<Cancion>) {
    LazyColumn {
        items(canciones) { cancion ->
            Text(text = "${cancion.title} - ${cancion.id}")
//            AsyncImage(
//                model = cancion.thumbImage
//            )
        }
    }
}
