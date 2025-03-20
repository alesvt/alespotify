package com.alespotify.model

import kotlinx.serialization.Serializable

@Serializable
data class Artist(
    val id: Id,
    val name: String,
    val image: String,
    val description: String,
    val albums: List<Album>?,
    val songs: List<Cancion>?
)
