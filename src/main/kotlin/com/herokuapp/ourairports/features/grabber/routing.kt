package com.herokuapp.ourairports.features.grabber

import com.herokuapp.ourairports.data.converters.ConverterFactory
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Application.updateRemoteFiles() {

    val converterFactory: ConverterFactory by inject()

    routing {
        get("/api/v1/updatebase") {
            grabToMemory(log, converterFactory)
            call.respond(HttpStatusCode.OK, "Downloading and mapping started")
        }
    }
}