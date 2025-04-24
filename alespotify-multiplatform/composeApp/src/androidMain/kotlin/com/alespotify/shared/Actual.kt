package com.alespotify.shared

import io.ktor.client.*
import io.ktor.client.engine.android.*

actual fun httpClient(config: HttpClientConfig<*>.() -> Unit): HttpClient {
    return HttpClient(Android) {
        config(this)
    }
}