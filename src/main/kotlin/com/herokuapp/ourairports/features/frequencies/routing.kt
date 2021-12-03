package com.herokuapp.ourairports.features.frequencies

import com.herokuapp.ourairports.features.utils.correctIcao
import com.herokuapp.ourairports.repository.FrequenciesRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Application.frequenciesJson() {
    val freqRepository: FrequenciesRepository by inject()

    routing {
        get("/api/v1/frequencies/{icao}") {
            val airportCode = call.parameters["icao"]
            log.debug("Get request for frequencies of $airportCode airport")
            correctIcao(airportCode)?.let { icao ->
                val freq = freqRepository.getByICAO(icao)
                freq?.let {
                    call.respond(HttpStatusCode.OK, freq)
                } ?: call.respond(
                    HttpStatusCode.NotFound, "Frequencies of airport with ICAO $icao not found"
                )
            } ?: call.respond(
                HttpStatusCode.BadRequest,
                "You should pass ICAO code instead of $airportCode"
            )
        }
    }
}