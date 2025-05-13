package com.alespotify.model

import kotlinx.serialization.Serializable
import org.mongodb.kbson.ObjectId

@Serializable
data class User(
    val id: String,
    val email: String,
    val password: String,
    val name: String,
    val imagen : String? = null,

)
