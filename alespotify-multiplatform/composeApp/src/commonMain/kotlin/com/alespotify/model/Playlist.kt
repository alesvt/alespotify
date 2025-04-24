package com.alespotify.model

import kotlinx.serialization.Serializable
import org.mongodb.kbson.ObjectId

@Serializable
data class Playlist(
    val id: String,
    val songs: List<Cancion>?,
    val image: String,
    val name: String,
    val creationDate: String,
    val updateDate: String,
    val publicPlaylist :Boolean
)
