package com.herokuapp.ourairports.repository

import com.herokuapp.ourairports.repository.model.Frequency

interface FrequenciesRepository {
    suspend fun getByICAO(code: String): List<Frequency>?
}