package com.herokuapp.ourairports.repository.model.enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class AirportType {
    @SerialName("closed_airport") CLOSED,
    @SerialName("heliport") HELIPORT,
    @SerialName("large_airport") LARGE,
    @SerialName("medium_airport") MEDIUM,
    @SerialName("seaplane_base") SEA_BASE,
    @SerialName("small_airport") SMALL
}

fun String.toAirportType() = when(this) {
    "closed_airport" -> AirportType.CLOSED
    "heliport" -> AirportType.HELIPORT
    "large_airport" -> AirportType.LARGE
    "medium_airport" -> AirportType.MEDIUM
    "seaplane_base" -> AirportType.SEA_BASE
    "small_airport" -> AirportType.SMALL
    else -> throw TypeCastException("Unknown airport type")
}