package com.alespotify.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.JsonIgnoreUnknownKeys
import org.mongodb.kbson.BsonObjectId as ObjectId

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
data class Cancion(
    val id: Float,
    val name: String,
    val artists: List<Artist>? = emptyList(),
    val genres: List<Genero>? = emptyList(),
    val duration: Long? = 0,
    val source: String,
    val image: String,
    val timesPlayed: Int? = 0,
)



