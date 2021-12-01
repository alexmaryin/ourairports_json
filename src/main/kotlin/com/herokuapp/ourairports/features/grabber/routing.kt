package com.herokuapp.ourairports.features.grabber

import com.herokuapp.ourairports.data.converters.ConverterFactory
import io.ktor.application.*
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.ktor.ext.inject

fun Application.updateRemoteFiles() {

    val converterFactory: ConverterFactory by inject()

    routing {
        get("/api/v1/updatebase") {

            with(HttpClient(CIO)) {
                FilesLinks.files.forEach { filename ->
                    downloadFile("files/$filename", "${FilesLinks.BASE_URL}/$filename?raw=true")
                        .onEach {
                            it.onError { _, error ->
                                log.debug("$filename download error: ${error?.localizedMessage}")
                            }
                            it.onSuccess {
                                log.debug("$filename successfully downloaded.")
                                val isSaved = converterFactory.convert(filename)
                                log.debug("$filename is ${if(isSaved) "" else "not"} mapped to database.")

                            }
                        }.launchIn(CoroutineScope(Dispatchers.IO))
                }
            }

            call.respond(HttpStatusCode.OK, "Downloading and mapping started")
        }
    }
}