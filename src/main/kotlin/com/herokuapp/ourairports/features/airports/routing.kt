package com.herokuapp.ourairports.features.airports

import com.herokuapp.ourairports.features.utils.correctIcao
import com.herokuapp.ourairports.repository.AirportsRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Application.airportsJson(

) {
    val airportsRepository: AirportsRepository by inject()

    routing {
        get("/api/v1/airports/{icao}") {
            val airportCode = call.parameters["icao"]
            log.debug("Get request for runways of $airportCode")
            correctIcao(airportCode)?.let { icao ->
                val airport = airportsRepository.getByICAO(icao)
                airport?.let {
                    call.respond(HttpStatusCode.OK, airport)
                } ?: call.respond(
                    HttpStatusCode.NotFound, "Airport with ICAO $icao not found"
                )
            } ?: call.respond(
                HttpStatusCode.BadRequest,
                "You should pass ICAO code instead of $airportCode"
            )
        }
    }
}