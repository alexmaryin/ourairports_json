package com.herokuapp.ourairports.features.runways

import com.herokuapp.ourairports.features.FeatureKoinTest
import com.herokuapp.ourairports.testObjects.TestRunways
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.Test
import kotlin.test.assertEquals

class RunwaysTest : FeatureKoinTest() {

    @Test
    fun `Correct request should return 200 status and runways json array`() {
        withRouting {
            handleRequest(HttpMethod.Get, "/api/v1/runways/UWLL").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(listOf(TestRunways.UWLL), Json.decodeFromString(response.content!!))
            }
        }
    }

    @Test
    fun `Incorrect ICAO request should return 400 status`() {
        withRouting {
            handleRequest(HttpMethod.Get, "/api/v1/runways/_UWLL").apply {
                assertEquals(HttpStatusCode.BadRequest, response.status())
                assertEquals("You should pass ICAO code instead of _UWLL", response.content)
            }
        }
    }

    @Test
    fun `Unknown ICAO request should return 404 status`() {
        withRouting {
            handleRequest(HttpMethod.Get, "/api/v1/runways/UUUU").apply {
                assertEquals(HttpStatusCode.NotFound, response.status())
                assertEquals("Runways of airport with ICAO UUUU not found", response.content)
            }
        }
    }
}