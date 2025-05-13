package com.alespotify.model

import io.realm.kotlin.internal.interop.Timestamp
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import org.mongodb.kbson.BsonObjectId as ObjectId

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
data class Artist (
    val id: Float,
    val name: String,
    val description : String,
    val image: String ? = null,
)
