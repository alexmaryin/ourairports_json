package com.herokuapp.ourairports.data.converters

import com.herokuapp.ourairports.data.model.AirportsTable
import com.herokuapp.ourairports.data.model.FrequenciesTable
import com.herokuapp.ourairports.data.model.RunwaysTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.replace
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File

class OurAirportsFactory(private val db: Database) : ConverterFactory {

    private fun getLinesAsSequence(filename: String, process: (Map<String, String>) -> Unit) {
        val headers = File("files/$filename").useLines {
            it.firstOrNull()?.replace("\"", "")?.split(",")
        } ?: throw RuntimeException("File $filename has not valid headers")
        File("files/$filename").useLines { file ->
            file.drop(1)
            .map { it.replace("\"", "").split(",") }
            .map { headers.zip(it).toMap() }
            .forEach(process)
        }
    }

    private fun replaceAirport(row: Map<String, String>) = AirportsTable.replace {
        row[id.name]?.toIntOrNull().let { airportId -> it[id] = airportId }
        it[icao] = row[icao.name].toString().take(4)
        it[type] = row["type"].toString().take(50)
        it[name] = row[name.name].toString().take(255)
        it[latitude] = row[latitude.name]?.toFloatOrNull() ?: 0f
        it[longitude] = row[longitude.name]?.toFloatOrNull() ?: 0f
        it[elevation] = row[elevation.name]?.toIntOrNull() ?: 0
        it[webSite] = row[webSite.name]
        it[wiki] = row[wiki.name]
    }


    private fun replaceRunway(row: Map<String, String>) = RunwaysTable.replace {
        row[id.name]?.toIntOrNull()?.let { runwayId -> it[id] = runwayId }
        it[airportId] = row[airportId.name]?.toIntOrNull() ?: 0
        it[lengthFeet] = row[lengthFeet.name]?.toIntOrNull() ?: 0
        it[widthFeet] = row[widthFeet.name]?.toIntOrNull() ?: 0
        it[surface] = row[surface.name].toString().take(10)
        it[closed] = row[closed.name].toBoolean()
        it[lowNumber] = row[lowNumber.name].toString().take(10)
        it[lowHeading] = row[lowHeading.name]?.toIntOrNull() ?: 0
        it[lowElevationFeet] = row[lowElevationFeet.name]?.toIntOrNull() ?: 0
        it[highNumber] = row[highNumber.name].toString().take(10)
        it[highHeading] = row[highHeading.name]?.toIntOrNull() ?: 0
        it[highElevationFeet] = row[highElevationFeet.name]?.toIntOrNull() ?: 0
    }

    private fun replaceFrequency(row: Map<String, String>) = FrequenciesTable.replace {
        row[id.name]?.toIntOrNull()?.let { freqId -> it[id] = freqId }
        it[airportId] = row[airportId.name]?.toIntOrNull() ?: 0
        it[type] = row["type"].toString().take(50)
        it[description] = row[description.name]?.take(100)
        it[valueMhz] = row[valueMhz.name]?.toFloatOrNull() ?: 0f

    }

    override fun convert(filename: String): Boolean {
        transaction(db) {
            getLinesAsSequence(filename) { row ->
                when (filename) {
                    "airports.csv" -> replaceAirport(row)
                    "runways.csv" -> replaceRunway(row)
                    "airport-frequencies.csv" -> replaceFrequency(row)
                    else -> throw RuntimeException("Mapper for $filename is not implemented!")
                }
            }
        }
        return true
    }
}