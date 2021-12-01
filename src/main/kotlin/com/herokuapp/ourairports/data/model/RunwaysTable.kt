package com.herokuapp.ourairports.data.model

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

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
