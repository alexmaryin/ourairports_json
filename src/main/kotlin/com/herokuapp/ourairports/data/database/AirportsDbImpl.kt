package com.herokuapp.ourairports.data.database

import com.herokuapp.ourairports.data.model.AirportsTable
import com.herokuapp.ourairports.data.model.RunwaysTable
import com.herokuapp.ourairports.data.model.toAirport
import com.herokuapp.ourairports.data.model.toRunway
import com.herokuapp.ourairports.repository.model.Airport
import com.herokuapp.ourairports.repository.model.Runway
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class AirportsDbImpl(
    private val db: Database
) : AirportsDb {

    override suspend fun getAirportByICAO(code: String): Airport? =
        transaction(db) {
            val airport = AirportsTable.select { AirportsTable.icao eq code }.firstOrNull()
            airport?.let {
                val runways = RunwaysTable.select { RunwaysTable.airportId eq it[AirportsTable.id].value }
                it.toAirport(emptyList(), runways.map { row -> row.toRunway() })
            }
        }

    override suspend fun getRunwaysByAirportICAO(code: String): List<Runway>? =
        getAirportByICAO(code)?.runways
}