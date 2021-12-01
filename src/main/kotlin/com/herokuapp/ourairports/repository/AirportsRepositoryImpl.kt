package com.herokuapp.ourairports.repository

import com.herokuapp.ourairports.data.database.AirportsDb
import com.herokuapp.ourairports.repository.model.Airport

class AirportsRepositoryImpl(
    private val db: AirportsDb
) : AirportsRepository {
    override suspend fun getByICAO(code: String): Airport? = db.getAirportByICAO(code)
}