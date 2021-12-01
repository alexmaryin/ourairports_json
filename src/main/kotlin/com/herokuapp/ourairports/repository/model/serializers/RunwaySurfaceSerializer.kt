package com.herokuapp.ourairports.repository.model.serializers

import com.herokuapp.ourairports.repository.model.enums.RunwaySurface
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object RunwaySurfaceSerializer : KSerializer<RunwaySurface> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("RunwaySurface", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): RunwaySurface = when(decoder.decodeString().uppercase()) {
        "ASP" -> RunwaySurface.ASPHALT
        "TURF" -> RunwaySurface.TURF
        "CON" -> RunwaySurface.CONCRETE
        "GRS" -> RunwaySurface.GRASS
        "GRE" -> RunwaySurface.GRAVEL
        "WATER" -> RunwaySurface.WATER
        else -> RunwaySurface.UNKNOWN
    }

    override fun serialize(encoder: Encoder, value: RunwaySurface) {
        encoder.encodeString(value.short)
    }
}