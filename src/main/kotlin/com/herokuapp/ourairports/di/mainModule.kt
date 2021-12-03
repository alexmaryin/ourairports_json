package com.herokuapp.ourairports.di

import com.herokuapp.ourairports.data.converters.ConverterFactory
import com.herokuapp.ourairports.data.converters.OurAirportsFactory
import com.herokuapp.ourairports.data.database.AirportsDb
import com.herokuapp.ourairports.data.database.AirportsDbImpl
import com.herokuapp.ourairports.data.model.AirportsTable
import com.herokuapp.ourairports.data.model.FrequenciesTable
import com.herokuapp.ourairports.data.model.RunwaysTable
import com.herokuapp.ourairports.features.grabber.AirportsGrabber
import com.herokuapp.ourairports.features.grabber.AirportsGrabberImpl
import com.herokuapp.ourairports.repository.*
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.dsl.module

val mainModule = module {

    val db = Database.connect("jdbc:h2:mem:regular;DB_CLOSE_DELAY=-1;", "org.h2.Driver")

    transaction(db) {
        SchemaUtils.create(AirportsTable, FrequenciesTable, RunwaysTable)
    }

    val client = HttpClient(CIO)

    single<ConverterFactory> { OurAirportsFactory(db) }
    single<AirportsDb> { AirportsDbImpl(db) }
    single<RunwaysRepository> { RunwaysRepositoryImpl(get()) }
    single<AirportsRepository> { AirportsRepositoryImpl(get()) }
    single<FrequenciesRepository> { FrequenciesRepositoryImpl(get()) }
    single<AirportsGrabber> { AirportsGrabberImpl(get(), client) }
}
