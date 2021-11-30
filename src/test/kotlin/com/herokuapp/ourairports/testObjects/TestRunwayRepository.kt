package com.herokuapp.ourairports.testObjects

import com.herokuapp.ourairports.repository.RunwaysRepository
import com.herokuapp.ourairports.repository.model.Runway

class TestRunwayRepository : RunwaysRepository {
    override fun getByICAO(code: String): List<Runway>? =
        if (code == "UWLL") TestRunways.UWLL else null
}