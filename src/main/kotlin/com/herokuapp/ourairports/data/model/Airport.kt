package com.herokuapp.ourairports.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Airport(
    val id: Int,
    @SerialName("ident") val icao: String,
    val type: AirportType,
    val name: String,
    @SerialName("latitude_deg") val latitude: Float,
    @SerialName("longitude_deg") val longitude: Float,
    @SerialName("elevation_ft") val elevation: Int,
    @SerialName("home_link") val webSite: String? = null,
    @SerialName("wikipedia_link") val wiki: String? = null
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
