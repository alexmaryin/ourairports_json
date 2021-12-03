package com.herokuapp.ourairports.repository

import com.herokuapp.ourairports.data.database.AirportsDb
import com.herokuapp.ourairports.repository.model.Frequency

class FrequenciesRepositoryImpl(
    private val database: AirportsDb
) : FrequenciesRepository {
    override suspend fun getByICAO(code: String): List<Frequency>? =
        database.getFrequenciesByAirportICAO(code)
}