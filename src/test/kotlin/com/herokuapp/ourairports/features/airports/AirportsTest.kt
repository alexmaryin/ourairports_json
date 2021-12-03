package com.herokuapp.ourairports.features.airports

import com.herokuapp.ourairports.features.FeatureKoinTest
import com.herokuapp.ourairports.testObjects.TestAirport
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.Test
import kotlin.test.assertEquals

class AirportsTest : FeatureKoinTest() {

    @Test
    fun `Correct request should return 200 status and frequencies json array`() {
        withRouting {
            handleRequest(HttpMethod.Get, "/api/v1/airports/UWLL").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(TestAirport.barataevka, Json.decodeFromString(response.content!!))
            }
        }
    }

    @Test
    fun `Incorrect ICAO request should return 400 status`() {
        withRouting {
            handleRequest(HttpMethod.Get, "/api/v1/airports/_UWLL").apply {
                assertEquals(HttpStatusCode.BadRequest, response.status())
                assertEquals("You should pass ICAO code instead of _UWLL", response.content)
            }
        }
    }

    @Test
    fun `Unknown ICAO request should return 404 status`() {
        withRouting {
            handleRequest(HttpMethod.Get, "/api/v1/airports/UUUU").apply {
                assertEquals(HttpStatusCode.NotFound, response.status())
                assertEquals("Airport with ICAO UUUU not found", response.content)
            }
        }
    }
}