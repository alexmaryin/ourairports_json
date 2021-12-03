package com.herokuapp.ourairports.features

import com.herokuapp.ourairports.data.converters.ConverterFactory
import com.herokuapp.ourairports.data.database.AirportsDb
import com.herokuapp.ourairports.data.database.AirportsDbImpl
import com.herokuapp.ourairports.data.model.AirportsTable
import com.herokuapp.ourairports.data.model.FrequenciesTable
import com.herokuapp.ourairports.data.model.RunwaysTable
import com.herokuapp.ourairports.features.grabber.AirportsGrabber
import com.herokuapp.ourairports.features.grabber.AirportsGrabberImpl
import com.herokuapp.ourairports.repository.AirportsRepository
import com.herokuapp.ourairports.repository.FrequenciesRepository
import com.herokuapp.ourairports.repository.RunwaysRepository
import com.herokuapp.ourairports.testObjects.MockConverterFactory
import com.herokuapp.ourairports.testObjects.TestAirportRepository
import com.herokuapp.ourairports.testObjects.TestFrequenciesRepository
import com.herokuapp.ourairports.testObjects.TestRunwayRepository
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.dsl.module

fun testModule(withDb: Boolean = false)  = module {
    if (withDb) {
        val db = Database.connect("jdbc:h2:mem:regular;DB_CLOSE_DELAY=-1;", "org.h2.Driver")
        transaction(db) {
            SchemaUtils.create(AirportsTable, FrequenciesTable, RunwaysTable)
        }
        single<AirportsDb> { AirportsDbImpl(db) }
    }

    val client = HttpClient(MockEngine {
        respondBadRequest()
    })

    single<AirportsRepository> { TestAirportRepository() }
    single<RunwaysRepository> { TestRunwayRepository() }
    single<FrequenciesRepository> { TestFrequenciesRepository() }
    single<ConverterFactory> { MockConverterFactory }
    single<AirportsGrabber> { AirportsGrabberImpl(get(), client) }
}