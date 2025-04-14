package com.alespotify.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys
import org.mongodb.kbson.BsonObjectId as ObjectId

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
data class Album(
    val id: String,
    val albumSongs: List<Cancion>,
    val albumImage: String,
    val albumReleaseDate : String,
    val albumArtist: Artist,
    val albumName: String,
)
