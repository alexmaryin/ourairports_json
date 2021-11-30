package com.herokuapp.ourairports.repository.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Airport(
    val icao: String,
    val type: AirportType,
    val name: String,
    val latitude: Float,
    val longitude: Float,
    val elevation: Int,
    val webSite: String? = null,
    val wiki: String? = null,
    val frequencies: List<Frequency> = emptyList(),
    val runways: List<Runway> = emptyList()
)

@Serializable
enum class AirportType {
    @SerialName("closed_airport") CLOSED,
    @SerialName("heliport") HELIPORT,
    @SerialName("large_airport") LARGE,
    @SerialName("medium_airport") MEDIUM,
    @SerialName("seaplane_base") SEA_BASE,
    @SerialName("small_airport") SMALL
}
