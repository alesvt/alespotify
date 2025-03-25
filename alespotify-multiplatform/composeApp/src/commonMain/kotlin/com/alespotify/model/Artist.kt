package com.alespotify.model

import kotlinx.serialization.Serializable
import org.mongodb.kbson.ObjectId

@Serializable
data class Artist(
    val id: ObjectId,
    val name: String,
    val image: String,
    val description: String,
    val albums: List<Album>?,
    val songs: List<Cancion>?
)
