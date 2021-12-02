package com.herokuapp.ourairports.di

import com.herokuapp.ourairports.data.converters.ConverterFactory
import com.herokuapp.ourairports.data.converters.OurAirportsFactory
import com.herokuapp.ourairports.data.database.AirportsDb
import com.herokuapp.ourairports.data.database.AirportsDbImpl
import com.herokuapp.ourairports.data.model.AirportsTable
import com.herokuapp.ourairports.data.model.FrequenciesTable
import com.herokuapp.ourairports.data.model.RunwaysTable
import com.herokuapp.ourairports.repository.AirportsRepository
import com.herokuapp.ourairports.repository.AirportsRepositoryImpl
import com.herokuapp.ourairports.repository.RunwaysRepository
import com.herokuapp.ourairports.repository.RunwaysRepositoryImpl
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.dsl.module

val mainModule = module {

//    val db = Database.connect("jdbc:h2:./files/database_v1", "org.h2.Driver")
    val url = System.getenv("DB_HOST") ?: "ec2-79-125-30-28.eu-west-1.compute.amazonaws.com:5432"
    val name = System.getenv("DB_NAME") ?: "dniqhm0ujhv77"
    val user = System.getenv("DB_USER") ?: "osyswcbzuxrbdv"
    val password = System.getenv("DB_PASSWORD") ?: "008f574aba660a765879bc487394b0712201ffc4eb138aa4dcaaa7c63477a5a4"
    val db = Database.connect(
        url = "jdbc:postgresql://$url/$name?sslmode=require",
        driver = "org.postgresql.Driver",
        user = user,
        password = password
    )
    transaction(db) {
        SchemaUtils.create(AirportsTable, FrequenciesTable, RunwaysTable)
    }

    single<ConverterFactory> { OurAirportsFactory(db) }
    single<AirportsDb> { AirportsDbImpl(db) }
    single<RunwaysRepository> { RunwaysRepositoryImpl(get()) }
    single<AirportsRepository> { AirportsRepositoryImpl(get()) }

}
