package com.herokuapp.ourairports.features.grabber

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.channelFlow
import java.io.File

@OptIn(ExperimentalCoroutinesApi::class)
suspend fun HttpClient.downloadFile(filename: String, url: String) = channelFlow {
    if (File(filename).exists()) {
        send(DownloadResult.Success)
    } else try {
        val response = get<HttpResponse>(url) {
            onDownload { bytesSentTotal, contentLength ->
                val percent = (bytesSentTotal / contentLength) * 100
                send(DownloadResult.Progress(percent.toInt()))
            }
        }
        val data: ByteArray = response.receive()
        File(filename).writeBytes(data)
        send(DownloadResult.Success)
    } catch (e: TimeoutCancellationException) {
        send(DownloadResult.Error("Connection timeout", e))
    } catch (e: Exception) {
        send(DownloadResult.Error("Unexpected error", e))
    }
}