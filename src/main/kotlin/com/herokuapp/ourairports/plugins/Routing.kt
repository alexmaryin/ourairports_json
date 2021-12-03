package com.herokuapp.ourairports.plugins

import com.herokuapp.ourairports.data.converters.ConverterFactory
import com.herokuapp.ourairports.features.airports.airportsJson
import com.herokuapp.ourairports.features.frequencies.frequenciesJson
import com.herokuapp.ourairports.features.grabber.grabToMemory
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
        val converter: ConverterFactory by inject()
        grabToMemory(log, converter)
    }

    routing {
        get("/info/title") { call.respondText("OurAirports-json API by java73") }
        get("/info/version") { call.respondText("1.0.0") }
        get("/info/description") { call.respondText("Allows to receive World airports information in JSON format") }
        get("/info/contact/name") { call.respondText("Alex Maryin") }
        get("/info/contact/url") { call.respondText("https://github.com/alexmaryin/ourairports_json") }
        get("/info/contact/email") { call.respondText("mailto://java.ul@gmail.com") }
        get("/info/x-api-id") { call.respondText("78f2dcde-540f-11ec-bf63-0242ac130002") }
        get("/info/x-audience") { call.respondText("external-public") }

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
