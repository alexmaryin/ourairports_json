package com.herokuapp.ourairports.features.grabber

object FilesLinks {
    const val BASE_URL = "https://github.com/davidmegginson/ourairports-data/blob/main"
    val files = listOf(
        "airports.csv",
//        "countries.csv",
//        "navaids.csv",
//        "regions.csv",
        "runways.csv",
        "airport-frequencies.csv"
    )
}