package com.herokuapp.ourairports.plugins

import com.herokuapp.ourairports.features.airports.airportsJson
import com.herokuapp.ourairports.features.frequencies.frequenciesJson
import com.herokuapp.ourairports.features.grabber.AirportsGrabber
import com.herokuapp.ourairports.features.grabber.FilesLinks
import com.herokuapp.ourairports.features.grabber.updateRemoteFiles
import com.herokuapp.ourairports.features.runways.runwaysJson
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.ktor.ext.inject

fun Application.configureRouting() {

    runwaysJson()
    airportsJson()
    frequenciesJson()
    updateRemoteFiles()

    launch(Dispatchers.IO) {
        val grabber: AirportsGrabber by inject()
        grabber.grabToDatabase(FilesLinks.files, FilesLinks.BASE_URL)
    }

    routing {

        get("/") {
                call.respondText("Hello World!")
            }
        install(StatusPages) {
            exception<AuthenticationException> {
                call.respond(HttpStatusCode.Unauthorized)
            }
            exception<AuthorizationException> {
                call.respond(HttpStatusCode.Forbidden)
            }
        
        }
        // Static plugin. Try to access `/static/index.html`
        static("/static") {
            defaultResource("static/index.html")
            resources("static")
        }
    }
}
class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()
