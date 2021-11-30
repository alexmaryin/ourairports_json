package com.herokuapp.ourairports.repository

import com.herokuapp.ourairports.repository.model.Runway

class RunwaysRepositoryImpl : RunwaysRepository {
    override fun getByICAO(code: String): List<Runway>? {
        TODO("Not yet implemented")
    }
}