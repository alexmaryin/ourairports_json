package com.herokuapp.ourairports.features.grabber

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Application.updateRemoteFiles() {

    val grabber: AirportsGrabber by inject()

    routing {
        get("/api/v1/updatebase") {
            grabber.grabToDatabase(FilesLinks.files, FilesLinks.BASE_URL)
            call.respond(HttpStatusCode.OK, "Downloading and mapping started")
        }
    }
}