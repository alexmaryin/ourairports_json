package com.herokuapp.ourairports.testObjects

import com.herokuapp.ourairports.repository.AirportsRepository
import com.herokuapp.ourairports.repository.model.Airport

class TestAirportRepository : AirportsRepository {
    override suspend fun getByICAO(code: String): Airport? =
        if (code == "UWLL") TestAirport.barataevka else null
}