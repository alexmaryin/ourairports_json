package com.herokuapp.ourairports.testObjects

import com.herokuapp.ourairports.repository.RunwaysRepository
import com.herokuapp.ourairports.repository.model.Runway

class TestRunwayRepository : RunwaysRepository {
    override suspend fun getByICAO(code: String): List<Runway>? =
        if (code == "UWLL") TestAirport.barataevka.runways else null
}