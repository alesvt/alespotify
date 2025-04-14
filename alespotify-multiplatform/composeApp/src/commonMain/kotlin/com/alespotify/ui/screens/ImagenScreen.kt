package com.alespotify.ui.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil3.compose.rememberAsyncImagePainter
import com.alespotify.model.Cancion
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.mongodb.kbson.ObjectId


val httpClient = HttpClient(CIO) {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }
}

@Composable
fun ImagenDesdeApiScreen() {
    var imageData: Cancion? by remember { mutableStateOf(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                imageData = httpClient.get("http://172.24.128.1:8080/api/songs/67d9331e22be08aece99c041").body()
            } catch (e: Exception) {
                errorMessage = "Error al obtener datos: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        } else if (imageData != null) {
            Text(imageData!!.title)
            Image(
                painter = rememberAsyncImagePainter(imageData!!.thumbImage),
                contentDescription = "Imagen de la API"
            )
        } else if (errorMessage != null) {
            Text(text = errorMessage!!)
        }
    }
}