package com.alespotify.shared

import com.alespotify.model.Artist
import com.alespotify.model.Cancion
import com.alespotify.model.Playlist
import com.alespotify.model.User
import io.ktor.client.call.body
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.serialization.Serializable


const val BASE_URL = "http://172.18.80.1:8080/api"

@Serializable
data class Credentials(val email: String, val password: String)


class ApiService {
    private val httpClient =
        createHttpClient() // Obtiene el HttpClient con la configuración común y el motor específico

    suspend fun login(email: String, password: String): User? {
        val response = httpClient.get("$BASE_URL/users/login") {
            contentType(ContentType.Application.Json)
            setBody(Credentials(email, password))
        }
        if (response.status == HttpStatusCode.OK) {
            // tendria que poner la ruta de main y cargar con otra llamada tal y tal
            // println("Login correcto: ${response.body<User>()}")
            return response.body() as User
        }
        print(response)
        return null
    }

    suspend fun getSongs(): List<Cancion>? {
        val response = httpClient.get("$BASE_URL/songs")
        if (response.status == HttpStatusCode.OK) {
            return response.body()
        }
        println("Error al obtener canciones: ${response.status}")
        return null
    }

    suspend fun getArtists(): List<Artist>? {
        val response = httpClient.get("$BASE_URL/artists")
        if (response.status == HttpStatusCode.OK) {
            return response.body()
        } else {
            println("Error al obtener artistas: ${response.status}")
            return null
        }
    }

    suspend fun getPlaylists(): List<Playlist>? {
        val response = httpClient.get("$BASE_URL/playlists")
        if (response.status == HttpStatusCode.OK) {
            return response.body()
        } else {
            Logger.DEFAULT.log("NO VA")
            println("Error al obtener playlists: ${response.status}")
            return null
        }
    }

    suspend fun getSongById(id: String): Cancion? {
        return try {
            val response = httpClient.get("$BASE_URL/songs/$id")
            if (response.status == HttpStatusCode.OK) {
                response.body() as Cancion
            } else {
                println("Error al obtener canción con ID $id: ${response.status}")
                null
            }
        } catch (e: Exception) {
            println("Error al obtener canción con ID $id: ${e.message}")
            null
        }
    }

    // ... otros métodos de la API
}