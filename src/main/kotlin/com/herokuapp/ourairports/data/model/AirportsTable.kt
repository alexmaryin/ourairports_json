package com.herokuapp.ourairports.data.model

import com.herokuapp.ourairports.repository.model.Airport
import com.herokuapp.ourairports.repository.model.Frequency
import com.herokuapp.ourairports.repository.model.Runway
import com.herokuapp.ourairports.repository.model.enums.AirportType
import com.herokuapp.ourairports.repository.model.enums.toAirportType
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow

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

fun ResultRow.toAirport(
    frequencies: List<Frequency>,
    runways: List<Runway>
) = Airport(
    icao = this[AirportsTable.icao],
    type = this[AirportsTable.type].toAirportType(),
    name = this[AirportsTable.name],
    latitude = this[AirportsTable.latitude],
    longitude = this[AirportsTable.longitude],
    elevation = this[AirportsTable.elevation],
    webSite = this[AirportsTable.webSite],
    wiki = this[AirportsTable.wiki],
    frequencies = frequencies,
    runways = runways
)
