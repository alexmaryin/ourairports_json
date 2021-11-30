package com.herokuapp.ourairports.repository

import com.herokuapp.ourairports.repository.model.Runway

interface RunwaysRepository {
    fun getByICAO(code: String): List<Runway>?
}