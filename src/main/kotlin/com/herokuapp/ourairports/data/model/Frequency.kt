package com.herokuapp.ourairports.data.model

import com.herokuapp.ourairports.repository.model.serializers.FrequencyTypeSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Frequency(
    val id: Int,
    @SerialName("airport_ref") val airportId: Int,
    @SerialName("airport_ident") val airportIcao: String,
    @Serializable(with = FrequencyTypeSerializer::class) val type: FrequencyType,
    val description: String? = null,
    @SerialName("frequency_mhz") val valueMhz: Float
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