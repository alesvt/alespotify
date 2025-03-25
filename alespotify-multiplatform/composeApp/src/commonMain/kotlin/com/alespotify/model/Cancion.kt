package com.alespotify.model

import kotlinx.serialization.Serializable
import org.mongodb.kbson.ObjectId

@Serializable
data class Cancion(
    val id: ObjectId,
    val title: String, // Cambiado de song_title a title
    val artists: List<Artist>, // Cambiado de song_artists a artists
    val album: Album?, // Cambiado de song_album a album
    val genres: List<String>, // Cambiado de song_genre a genres
    val length: Int, // Cambiado de song_length a length
    val source: String, // Cambiado de song_source a source
    val thumbImage: String, // Cambiado de song_thumb_image a thumbImage
    val timesPlayed: Int, // Cambiado de times_played a timesPlayed
    val addedToPlaylists: Int
)


