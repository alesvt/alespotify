package com.alespotify.shared

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.LogLevel
import kotlinx.serialization.json.Json
import io.ktor.client.plugins.logging.Logging

val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
    encodeDefaults = true
}

fun createHttpClient(config: HttpClientConfig<*>.() -> Unit = {}): HttpClient {
    return httpClient { // Llama a la función expect, que se resolverá a la actual de cada plataforma
        install(ContentNegotiation) {
            json(json)
        }
        install(Logging) {
            val logger = object : Logger {
                override fun log(message: String) {
                    println("Ktor: $message")
                }
            }
            level = LogLevel.ALL
        }
        config(this)
    }
}