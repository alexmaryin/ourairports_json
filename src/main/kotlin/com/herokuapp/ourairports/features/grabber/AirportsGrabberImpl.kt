package com.herokuapp.ourairports.features.grabber

import com.herokuapp.ourairports.data.converters.ConverterFactory
import io.ktor.client.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class AirportsGrabberImpl(
    private val converter: ConverterFactory,
    private val client: HttpClient
) : AirportsGrabber {
    override suspend fun grabToDatabase(files: List<String>, url: String) {
        with(client) {
            files.forEach { filename ->
                downloadFile("files/$filename", "$url/$filename")
                    .onEach {
                        it.onError { _, error ->
                            println("$filename download error: ${error?.localizedMessage}")
                        }
                        it.onSuccess {
                            println("$filename successfully downloaded.")
                            val isSaved = converter.convert(filename)
                            println("$filename is ${if (isSaved) "" else "not"} mapped to database.")

                        }
                    }.launchIn(CoroutineScope(Dispatchers.IO))
            }
        }
    }
}