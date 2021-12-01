package com.herokuapp.ourairports.repository.model

import com.herokuapp.ourairports.data.model.RunwaysTable
import com.herokuapp.ourairports.repository.model.enums.RunwaySurface
import com.herokuapp.ourairports.repository.model.serializers.RunwaySurfaceSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.encoding.Decoder
import org.jetbrains.exposed.sql.ResultRow

@Serializable
data class Runway(
    val lengthFeet: Int? = null,
    val widthFeet: Int? = null,
    @Serializable(with = RunwaySurfaceSerializer::class) val surface: RunwaySurface,
    val closed: Boolean,
    val lowNumber: String,
    val lowElevationFeet: Int? = null,
    val lowHeading: Int,
    val highNumber: String,
    val highElevationFeet: Int? = null,
    val highHeading: Int,
)

fun ResultRow.toRunway() = Runway(
    lengthFeet = this[RunwaysTable.lengthFeet],
    widthFeet = this[RunwaysTable.widthFeet],
    surface = RunwaySurfaceSerializer.getByString(this[RunwaysTable.surface]),
    closed = this[RunwaysTable.closed],
    lowNumber = this[RunwaysTable.lowNumber],
    lowElevationFeet = this[RunwaysTable.lowElevationFeet],
    lowHeading = this[RunwaysTable.lowHeading],
    highNumber = this[RunwaysTable.highNumber],
    highElevationFeet = this[RunwaysTable.highElevationFeet],
    highHeading = this[RunwaysTable.highHeading]
)
