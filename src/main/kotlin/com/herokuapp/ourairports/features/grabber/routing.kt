package com.herokuapp.ourairports.features.grabber

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

fun Application.updateRemoteFiles() {

    routing {
        get("/api/v1/updatebase") {

            with(HttpClient(CIO)) {
                FilesLinks.files.forEach { filename ->
                    downloadFile("files/$filename", "${FilesLinks.BASE_URL}/$filename?raw=true")
                        .onEach {
                            when(it) {
                                is DownloadResult.Error -> log.debug("$filename download error: ${it.error?.localizedMessage}")
                                is DownloadResult.Progress -> Unit
                                DownloadResult.Success -> log.debug("$filename successfully downloaded.")
                            }
                        }.launchIn(CoroutineScope(Dispatchers.IO))
                }
            }

            call.respond(HttpStatusCode.OK, "Downloading started")
        }
    }
}