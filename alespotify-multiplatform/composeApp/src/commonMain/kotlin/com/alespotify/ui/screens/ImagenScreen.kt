package com.alespotify.ui.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
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
import androidx.compose.ui.platform.*
import coil3.PlatformContext
import coil3.compose.LocalPlatformContext
import org.jetbrains.compose.ui.*


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
            AsyncImage(
                model = ImageRequest.Builder(context = LocalPlatformContext.current)
                    .data("https://png.pngtree.com/png-vector/20221108/ourmid/pngtree-cartoon-house-illustration-png-image_6434928.png")
                    .build(),
                contentDescription = "imagen API",
                modifier = Modifier.fillMaxWidth()
            )
        } else if (errorMessage != null) {
            Text(text = errorMessage!!)
        }
    }
}