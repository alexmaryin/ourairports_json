package com.herokuapp.ourairports.data.model

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

object AirportsTable : IntIdTable() {
    val icao: Column<String> = varchar("ident", 4)
    val type: Column<String> = varchar("airport_type", 50)
    val name: Column<String> = varchar("name", 255)
    val latitude: Column<Float> = float("latitude_deg")
    val longitude: Column<Float> = float("longitude_deg")
    val elevation: Column<Int> = integer("elevation_ft")
    val webSite: Column<String?> = varchar("home_link", 300).nullable()
    val wiki: Column<String?> = varchar("wikipedia_link", 300).nullable()
}
