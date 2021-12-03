package com.herokuapp.ourairports.data.database

import com.herokuapp.ourairports.repository.model.Airport
import com.herokuapp.ourairports.repository.model.Frequency
import com.herokuapp.ourairports.repository.model.Runway

interface AirportsDb {
    suspend fun getAirportByICAO(code: String): Airport?
    suspend fun getRunwaysByAirportICAO(code: String): List<Runway>?
    suspend fun getFrequenciesByAirportICAO(code: String): List<Frequency>?
}