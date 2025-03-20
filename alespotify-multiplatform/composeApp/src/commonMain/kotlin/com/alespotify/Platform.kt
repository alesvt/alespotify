package com.alespotify

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform