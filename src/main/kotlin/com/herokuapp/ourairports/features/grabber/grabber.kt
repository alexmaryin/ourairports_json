package com.herokuapp.ourairports.features.grabber

import com.herokuapp.ourairports.data.converters.ConverterFactory
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.slf4j.Logger

suspend fun grabToMemory(log: Logger, converter: ConverterFactory) {

    with(HttpClient(CIO)) {
        FilesLinks.files.forEach { filename ->
            downloadFile("files/$filename", "${FilesLinks.BASE_URL}/$filename?raw=true")
                .onEach {
                    it.onError { _, error ->
                        log.debug("$filename download error: ${error?.localizedMessage}")
                    }
                    it.onSuccess {
                        log.debug("$filename successfully downloaded.")
                        val isSaved = converter.convert(filename)
                        log.debug("$filename is ${if (isSaved) "" else "not"} mapped to database.")

                    }
                }.launchIn(CoroutineScope(Dispatchers.IO))
        }
    }
}