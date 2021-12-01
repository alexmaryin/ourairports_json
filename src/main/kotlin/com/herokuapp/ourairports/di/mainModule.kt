package com.herokuapp.ourairports.di

import com.herokuapp.ourairports.data.converters.ConverterFactory
import com.herokuapp.ourairports.data.converters.OurAirportsFactory
import com.herokuapp.ourairports.data.database.AirportsDb
import com.herokuapp.ourairports.data.database.AirportsDbImpl
import com.herokuapp.ourairports.data.model.AirportsTable
import com.herokuapp.ourairports.data.model.FrequenciesTable
import com.herokuapp.ourairports.data.model.RunwaysTable
import com.herokuapp.ourairports.repository.RunwaysRepository
import com.herokuapp.ourairports.repository.RunwaysRepositoryImpl
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.dsl.module

val mainModule = module {

    val db = Database.connect("jdbc:h2:./files/database_v1", "org.h2.Driver")
    transaction(db) {
        SchemaUtils.create(AirportsTable, FrequenciesTable, RunwaysTable)
    }

    single<ConverterFactory> { OurAirportsFactory(db) }
    single<AirportsDb> { AirportsDbImpl(db) }
    single<RunwaysRepository> { RunwaysRepositoryImpl(get()) }

}
