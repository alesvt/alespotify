package com.alespotify.ui

import com.alespotify.model.Cancion
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.coroutines.*
import kotlinx.serialization.json.buildJsonArray


class ApiClient {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true

            })

        }
    }


    /*
    para llamada POST
    val response: HttpResponse = client.post("http://localhost:8080/customer") {
    contentType(ContentType.Application.Json)
    setBody(Customer(3, "Jet", "Brains"))
}
     */
    suspend fun obtenerCanciones(): List<Cancion> {
        try {
            return httpClient.get("https://picsum.photos/450/320").body()
        } catch (e: Exception) {
            println("Error: ${e.message}")
        } finally {
            httpClient.close();
        }
        return emptyList()
    }



}