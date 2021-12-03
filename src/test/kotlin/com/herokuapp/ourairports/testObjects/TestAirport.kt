package com.herokuapp.ourairports.testObjects

import com.herokuapp.ourairports.repository.model.Airport
import com.herokuapp.ourairports.repository.model.enums.AirportType

object TestAirport {
    val barataevka = Airport(
        icao = "UWLL",
        type = AirportType.MEDIUM,
        name = "Ulyanovsk Barataevka",
        latitude = 0.0f,
        longitude = 0.0f,
        elevation = 400,
        webSite = "ulk.aero",
        wiki = null,
        frequencies = listOf(TestFrequencies.unicom, TestFrequencies.atis),
        runways = listOf(TestRunways.UWLL)

    )
}