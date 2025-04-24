package com.alespotify.shared

import io.ktor.client.*
import io.ktor.client.engine.ios.*
import io.ktor.client.engine.darwin.Darwin

actual fun httpClient(config: HttpClientConfig<*>.() -> Unit): HttpClient {
    return HttpClient(Darwin) {
        config(this)
    }
}