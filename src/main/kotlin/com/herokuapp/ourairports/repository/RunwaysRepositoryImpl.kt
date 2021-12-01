package com.herokuapp.ourairports.repository

import com.herokuapp.ourairports.data.database.AirportsDb
import com.herokuapp.ourairports.repository.model.Runway

class RunwaysRepositoryImpl(
    private val database: AirportsDb
) : RunwaysRepository {
    override suspend fun getByICAO(code: String): List<Runway>? =
        database.getRunwaysByAirportICAO(code)
}