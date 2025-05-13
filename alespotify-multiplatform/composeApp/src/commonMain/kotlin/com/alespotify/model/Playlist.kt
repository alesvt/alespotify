package com.alespotify.model

import kotlinx.serialization.Serializable

// todo should i let images be null?
@Serializable
data class Playlist(
    val id: String,
    val nombre: String,
    val songs: List<Cancion>?,
    val image: String ?,
    val user: User,
    val creationDate: String ? = null,
    val updateDate: String ? = null,
    val isPublic: Boolean
)
