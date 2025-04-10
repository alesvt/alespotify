package com.alespotify.model

import kotlinx.serialization.Serializable
import org.mongodb.kbson.ObjectId

@Serializable
data class Playlist(
    val id: ObjectId,
    val songs: List<Cancion>?,
    val image: String,
    val creationDate: String,
    val updateDate: String,
    val public :Boolean,
    val user: User
)
