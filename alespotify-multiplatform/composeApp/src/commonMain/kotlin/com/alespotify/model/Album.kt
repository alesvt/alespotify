package com.alespotify.model

import kotlinx.serialization.Serializable

@Serializable
data class Album(
    val id: Id,
    val album_songs: List<Cancion>,
    val album_image: String,
    val album_release_date : String,
    val album_artist: Artist,
    val album_name: String,
)
