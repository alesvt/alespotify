package com.alespotify.model

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.mongodb.kbson.BsonObjectId

class ObjectIdSerializer : KSerializer<BsonObjectId> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("ObjectId", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: BsonObjectId) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): BsonObjectId {
        return BsonObjectId(decoder.decodeString())
    }


}
