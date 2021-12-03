package com.herokuapp.ourairports.data.database

import com.herokuapp.ourairports.data.model.*
import com.herokuapp.ourairports.repository.model.Airport
import com.herokuapp.ourairports.repository.model.Frequency
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
                val id = it[AirportsTable.id].value
                val runways = RunwaysTable.select { RunwaysTable.airportId eq id }
                val frequencies = FrequenciesTable.select { FrequenciesTable.airportId eq id }
                it.toAirport(
                    frequencies = frequencies.map { row -> row.toFrequency() },
                    runways = runways.map { row -> row.toRunway() }
                )
            }
        }

    override suspend fun getRunwaysByAirportICAO(code: String): List<Runway>? =
        getAirportByICAO(code)?.runways

    override suspend fun getFrequenciesByAirportICAO(code: String): List<Frequency>? =
        getAirportByICAO(code)?.frequencies
}