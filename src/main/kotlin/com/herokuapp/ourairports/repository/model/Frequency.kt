package com.herokuapp.ourairports.repository.model

import com.herokuapp.ourairports.repository.model.serializers.FrequencyTypeSerializer
import kotlinx.serialization.Serializable

@Serializable
data class Frequency(
    @Serializable(with = FrequencyTypeSerializer::class) val type: FrequencyType,
    val description: String? = null,
    val valueMhz: Float
)

enum class FrequencyType(val short: String) {
    TERMINAL("TML"),
    ADVISORY_SERVICE("CTAF"),
    RADAR("CTR"),
    UNRECOGNIZED("UNKNOWN"),
    FLIGHT_INFORMATION_SERVICE("AFIS"),
    APPROACH("APP"),
    APRON("RMP"),
    ARRIVAL("ARR"),
    WEATHER_OBSERVATION("ATIS"),
    UNICOM("UNIC"),
    DELIVERY("DEL"),
    RADAR_CONTROL("CTR"),
    DEPARTURE("DEP"),
    EMERGENCY("FIRE"),
    GROUND("GND"),
    TOWER("TWR"),
}