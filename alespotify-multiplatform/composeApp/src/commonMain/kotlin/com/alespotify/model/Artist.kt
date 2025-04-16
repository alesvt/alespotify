package com.alespotify.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys
import org.mongodb.kbson.BsonObjectId as ObjectId

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
data class Artist(
    val id: String,
    val name: String ? = null,
    val image: String ? = null,
    val description: String? = null,
    val albums: List<Album>? = null,
    val songs: List<Cancion>? = null
)
