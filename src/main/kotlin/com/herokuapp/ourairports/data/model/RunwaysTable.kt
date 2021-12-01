package com.herokuapp.ourairports.data.model

import com.herokuapp.ourairports.repository.model.Runway
import com.herokuapp.ourairports.repository.model.serializers.RunwaySurfaceSerializer
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow

object RunwaysTable : IntIdTable() {
    val airportId: Column<Int> = integer("airport_ref")
    val lengthFeet: Column<Int?> = integer("length_ft").nullable()
    val widthFeet: Column<Int?> = integer("width_ft").nullable()
    val surface: Column<String> = varchar("surface", 10)
    val closed: Column<Boolean> = bool("closed")
    val lowNumber: Column<String> = varchar("le_ident", 10)
    val lowElevationFeet: Column<Int?> = integer("le_elevation_ft").nullable()
    val lowHeading: Column<Int> = integer("le_heading_degT")
    val highNumber: Column<String> = varchar("he_ident", 10)
    val highElevationFeet: Column<Int?> = integer("he_elevation_ft").nullable()
    val highHeading: Column<Int> = integer("he_heading_degT")
}


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
