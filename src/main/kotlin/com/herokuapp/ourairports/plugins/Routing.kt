package com.herokuapp.ourairports.plugins

import com.herokuapp.ourairports.features.runways.runwaysJson
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.configureRouting() {

    runwaysJson()

    routing {
        get("/") {
                call.respondText("Hello World!")
            }
        install(StatusPages) {
            exception<AuthenticationException> { cause ->
                call.respond(HttpStatusCode.Unauthorized)
            }
            exception<AuthorizationException> { cause ->
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
