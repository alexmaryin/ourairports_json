package com.herokuapp.ourairports.features.grabber

sealed class DownloadResult {
    object Success : DownloadResult()
    data class Progress(val step: Int) : DownloadResult()
    data class Error(val message: String, val error: Exception? = null) : DownloadResult()
}

fun DownloadResult.onSuccess(action: () -> Unit) {
    if (this is DownloadResult.Success) action()
}

fun DownloadResult.onError(action: (message: String, error: Exception?) -> Unit) {
    if(this is DownloadResult.Error) action(message, error)
}

fun DownloadResult.onProgress(action: (step: Int) -> Unit) {
    if(this is DownloadResult.Progress) action(step)
}
