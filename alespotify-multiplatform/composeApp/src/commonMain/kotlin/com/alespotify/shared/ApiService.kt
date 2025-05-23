package com.alespotify.shared

import com.alespotify.model.Artist
import com.alespotify.model.Cancion
import com.alespotify.model.Playlist
import com.alespotify.model.User
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.append
import io.ktor.http.contentType
import io.ktor.http.parameters
import io.ktor.http.parametersOf
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json


const val BASE_URL = "http://172.205.130.42/api"

@Serializable
data class Credentials(val email: String, val password: String)

class ApiService {
    private val httpClient =
        createHttpClient()

    suspend fun login(email: String, password: String): User? {
        val response = httpClient.post("$BASE_URL/users/login") {
            contentType(ContentType.Application.Json)
            setBody(Credentials(email, password))
        }
        if (response.status == HttpStatusCode.OK) {
            return response.body() as User
        }
        print(response)
        return null
    }

    suspend fun getSongs(): List<Cancion>? {
        try {
            val response = httpClient.get("$BASE_URL/songs")
            if (response.status == HttpStatusCode.OK) {
                val songs = response.body<List<Cancion>>()
                return songs
            }
            println("Error al obtener canciones: ${response.status}")
            return null
        } catch (e: Exception) {
            println("Excepción al obtener canciones: ${e.message}")
            return null
        }
    }


    suspend fun getArtists(): List<Artist>? {
        val response = httpClient.get("$BASE_URL/artists")
        if (response.status == HttpStatusCode.OK) {
            return response.body() as List<Artist>
        } else {
            println("Error al obtener artistas: ${response.status}")
            return null
        }
    }

    suspend fun getPlaylists(): List<Playlist>? {
        val response = httpClient.get("$BASE_URL/playlists/public")
        if (response.status == HttpStatusCode.OK) {
            return response.body() as List<Playlist>
        } else {
            println("Error al obtener playlists: ${response.status}")
            return null
        }
    }

    suspend fun getSongById(id: Float): Cancion? {
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

    suspend fun createPlaylist(playlist: Playlist): Playlist? {
        return try {
            val response = httpClient.post("") {
                contentType(ContentType.Application.Json)
                setBody(playlist)
            }
            if (response.status == HttpStatusCode.OK) {
                response.body() as Playlist
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    suspend fun registerUser(
        nombre: String,
        email: String,
        password: String,
        image: String
    ): User? {
        return try {
            val response = httpClient.submitForm(
                url = "$BASE_URL/users/register",
                formParameters = parameters {
                    append("name", nombre)
                    append("image", image)
                    append("email", email)
                    append("password", password)
                }
            )
            if (response.status == HttpStatusCode.OK) {
                response.body() as User
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }


}