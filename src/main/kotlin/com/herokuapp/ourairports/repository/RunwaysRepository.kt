package com.herokuapp.ourairports.repository

import com.herokuapp.ourairports.repository.model.Runway

interface RunwaysRepository {
    suspend fun getByICAO(code: String): List<Runway>?
}