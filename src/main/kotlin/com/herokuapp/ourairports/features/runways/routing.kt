package com.herokuapp.ourairports.features.runways

import com.herokuapp.ourairports.features.runways.utils.correctIcao
import com.herokuapp.ourairports.repository.RunwaysRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Application.runwaysJson(

) {
    val runwaysRepository: RunwaysRepository by inject()

    routing {
        get("/api/v1/runways/{icao}") {
            val airportCode = call.parameters["icao"]
            log.debug("Get request for runways of $airportCode")
            correctIcao(airportCode)?.let { icao ->
                val runways = runwaysRepository.getByICAO(icao)
                runways?.let {
                    call.respond(HttpStatusCode.OK, runways)
                } ?: call.respond(
                    HttpStatusCode.NotFound, "Runways of airport with ICAO $icao not found"
                )
            } ?: call.respond(
                HttpStatusCode.BadRequest,
                "You should pass ICAO code instead of $airportCode"
            )
        }
    }
}