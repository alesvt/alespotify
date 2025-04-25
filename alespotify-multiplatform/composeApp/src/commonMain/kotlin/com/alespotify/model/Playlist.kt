package com.alespotify.model

import kotlinx.serialization.Serializable


@Serializable
data class Playlist(
    val id: String,
    val songs: List<Cancion>?,
    val image: String,
    val name: String,
    val user: User ? = null ,
    val creationDate: String ? = null,
    val updateDate: String ? = null,
    val publicPlaylist: Boolean
)
