package com.herokuapp.ourairports.features.grabber

sealed class DownloadResult {
    object Success : DownloadResult()
    data class Progress(val step: Int) : DownloadResult()
    data class Error(val message: String, val error: Exception? = null) : DownloadResult()
}
