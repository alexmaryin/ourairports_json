package com.herokuapp.ourairports.data.model

import com.herokuapp.ourairports.repository.model.serializers.RunwaySurfaceSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Runway(
    val id: Int,
    @SerialName("airport_ref") val airportId: Int,
    @SerialName("airport_ident") val airportIcao: String,
    @SerialName("length_ft") val lengthFeet: Int? = null,
    @SerialName("width_ft") val widthFeet: Int? = null,
    @Serializable(with = RunwaySurfaceSerializer::class) val surface: RunwaySurface,
    val closed: Boolean,
    @SerialName("le_ident") val lowNumber: String,
    @SerialName("le_elevation_ft") val lowElevationFeet: Int? = null,
    @SerialName("le_heading_degT") val lowHeading: Int,
    @SerialName("he_ident") val highNumber: String,
    @SerialName("he_elevation_ft") val highElevationFeet: Int? = null,
    @SerialName("he_heading_degT") val highHeading: Int,
)

@Serializable
enum class RunwaySurface(val short: String) {
    ASPHALT("ASP"),
    TURF("TURF"),
    CONCRETE("CON"),
    GRASS("GRS"),
    GRAVEL("GRE"),
    WATER("WATER"),
    UNKNOWN("UNK")
}