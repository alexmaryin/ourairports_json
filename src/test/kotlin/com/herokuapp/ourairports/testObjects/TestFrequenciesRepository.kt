package com.herokuapp.ourairports.testObjects

import com.herokuapp.ourairports.repository.FrequenciesRepository
import com.herokuapp.ourairports.repository.model.Frequency

class TestFrequenciesRepository : FrequenciesRepository {
    override suspend fun getByICAO(code: String): List<Frequency>? =
        if (code == "UWLL") TestAirport.barataevka.frequencies else null
}