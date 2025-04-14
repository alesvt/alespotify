package com.alespotify.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys
import org.mongodb.kbson.BsonObjectId as ObjectId

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class Artist(
    val id: String,
    val name: String,
    val image: String,
    val description: String,
    val albums: List<Album>?,
    val songs: List<Cancion>? = null
)
