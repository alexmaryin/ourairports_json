package com.herokuapp.ourairports.repository.model.serializers

import com.herokuapp.ourairports.repository.model.enums.FrequencyType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object FrequencyTypeSerializer : KSerializer<FrequencyType> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("FrequencyType", PrimitiveKind.STRING)

    fun getByString(value: String) = when(value.uppercase()) {
        "A/D", "A/G", "DIR", "TML" -> FrequencyType.TERMINAL
        "AAS", "CTAF" -> FrequencyType.ADVISORY_SERVICE
        "AFIS", "FIS", "INFO" -> FrequencyType.FLIGHT_INFORMATION_SERVICE
        "APP", "APPR", "APPROACH" -> FrequencyType.APPROACH
        "APRON", "RMP" -> FrequencyType.APRON
        "ARR" -> FrequencyType.ARRIVAL
        "ASOS", "AWOS", "ATIS" -> FrequencyType.WEATHER_OBSERVATION
        "ATF", "UNICOM", "UNIC" -> FrequencyType.UNICOM
        "CLD", "CLEARANCE", "DEL", "DELIVERY", "DLV", "GCD", "GCCD" -> FrequencyType.DELIVERY
        "ACC", "ACC SECTOR", "CNTR", "CTR", "CONTROL", "CTRL", "FIA", "FIR", "FSS", "RAD", "RADAR", "RDR" -> FrequencyType.RADAR_CONTROL
        "DEP", "DEPT", "DPR" -> FrequencyType.DEPARTURE
        "EMR", "FIRE" -> FrequencyType.EMERGENCY
        "GND", "GRND", "GROUND" -> FrequencyType.GROUND
        "TOWER", "TWR" -> FrequencyType.TOWER
        else -> FrequencyType.UNRECOGNIZED
    }

    override fun deserialize(decoder: Decoder): FrequencyType =
        getByString(decoder.decodeString())

    override fun serialize(encoder: Encoder, value: FrequencyType) {
        encoder.encodeString(value.short)
    }
}