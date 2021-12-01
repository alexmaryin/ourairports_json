package com.herokuapp.ourairports.data.converters

import com.herokuapp.ourairports.data.model.AirportsTable
import com.herokuapp.ourairports.data.model.FrequenciesTable
import com.herokuapp.ourairports.data.model.RunwaysTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.batchReplace
import org.jetbrains.exposed.sql.statements.BatchReplaceStatement
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File

class OurAirportsFactory(private val db: Database) : ConverterFactory {

    private fun getLinesAsSequence(filename: String): List<Map<String, String>> {
        val headers = File("files/$filename").useLines {
            it.firstOrNull()?.replace("\"", "")?.split(",")
        } ?: throw RuntimeException("File $filename has not valid headers")
        return File("files/$filename").readLines()
            .drop(1)
            .map { it.replace("\"", "").split(",") }
            .map { headers.zip(it).toMap() }
    }

    private fun replaceAirports(receiver: BatchReplaceStatement, row: Map<String, String>) {
        with(receiver) {
            row[AirportsTable.id.name]?.toIntOrNull().let { airportId -> this[AirportsTable.id] = airportId }
            this[AirportsTable.icao] = row[AirportsTable.icao.name].toString().take(4)
            this[AirportsTable.type] = row["type"].toString().take(50)
            this[AirportsTable.name] = row[AirportsTable.name.name].toString().take(255)
            this[AirportsTable.latitude] = row[AirportsTable.latitude.name]?.toFloatOrNull() ?: 0f
            this[AirportsTable.longitude] = row[AirportsTable.longitude.name]?.toFloatOrNull() ?: 0f
            this[AirportsTable.elevation] = row[AirportsTable.elevation.name]?.toIntOrNull() ?: 0
            this[AirportsTable.webSite] = row[AirportsTable.webSite.name]
            this[AirportsTable.wiki] = row[AirportsTable.wiki.name]
        }
    }

    private fun replaceRunways(receiver: BatchReplaceStatement, row: Map<String, String>) {
        with(receiver) {
            row[RunwaysTable.id.name]?.toIntOrNull()?.let { runwayId -> this[RunwaysTable.id] = runwayId }
            this[RunwaysTable.airportId] = row[RunwaysTable.airportId.name]?.toIntOrNull() ?: 0
            this[RunwaysTable.lengthFeet] = row[RunwaysTable.lengthFeet.name]?.toIntOrNull() ?: 0
            this[RunwaysTable.widthFeet] = row[RunwaysTable.widthFeet.name]?.toIntOrNull() ?: 0
            this[RunwaysTable.surface] = row[RunwaysTable.surface.name].toString().take(10)
            this[RunwaysTable.closed] = row[RunwaysTable.closed.name].toBoolean()
            this[RunwaysTable.lowNumber] = row[RunwaysTable.lowNumber.name].toString().take(10)
            this[RunwaysTable.lowHeading] = row[RunwaysTable.lowHeading.name]?.toIntOrNull() ?: 0
            this[RunwaysTable.lowElevationFeet] = row[RunwaysTable.lowElevationFeet.name]?.toIntOrNull() ?: 0
            this[RunwaysTable.highNumber] = row[RunwaysTable.highNumber.name].toString().take(10)
            this[RunwaysTable.highHeading] = row[RunwaysTable.highHeading.name]?.toIntOrNull() ?: 0
            this[RunwaysTable.highElevationFeet] = row[RunwaysTable.highElevationFeet.name]?.toIntOrNull() ?: 0
        }
    }

    private fun replaceFrequencies(receiver: BatchReplaceStatement, row: Map<String, String>) {
        with(receiver) {
            row[FrequenciesTable.id.name]?.toIntOrNull()?.let { freqId -> this[FrequenciesTable.id] = freqId }
            this[FrequenciesTable.airportId] = row[FrequenciesTable.airportId.name]?.toIntOrNull() ?: 0
            this[FrequenciesTable.type] = row["type"].toString().take(50)
            this[FrequenciesTable.description] = row[FrequenciesTable.description.name]?.take(100)
            this[FrequenciesTable.valueMhz] = row[FrequenciesTable.valueMhz.name]?.toFloatOrNull() ?: 0f
        }
    }

    override fun convert(filename: String): Boolean {
        val batch = getLinesAsSequence(filename)
        transaction(db) {
            when (filename) {
                "airports.csv" -> AirportsTable.batchReplace(batch) { row -> replaceAirports(this, row) }
                "runways.csv" -> RunwaysTable.batchReplace(batch) { row -> replaceRunways(this, row) }
                "airport-frequencies.csv" -> FrequenciesTable.batchReplace(batch) { row -> replaceFrequencies(this, row) }
                else -> throw RuntimeException("Mapper for $filename is not implemented!")
            }
        }
        return true
    }
}