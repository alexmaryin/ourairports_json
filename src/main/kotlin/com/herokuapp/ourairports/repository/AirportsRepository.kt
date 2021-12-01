package com.herokuapp.ourairports.repository

import com.herokuapp.ourairports.repository.model.Airport

interface AirportsRepository {
    suspend fun getByICAO(code: String): Airport?
}