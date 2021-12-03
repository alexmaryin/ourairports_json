package com.herokuapp.ourairports.features.grabber

interface AirportsGrabber {
    suspend fun grabToDatabase(files: List<String>, url: String)
}