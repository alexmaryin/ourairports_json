package com.herokuapp.ourairports.features.frequencies

import com.herokuapp.ourairports.features.FeatureKoinTest
import com.herokuapp.ourairports.testObjects.TestFrequencies
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.Test
import kotlin.test.assertEquals

class FrequenciesTest : FeatureKoinTest() {

    @Test
    fun `Correct request should return 200 status and frequencies json array`() {
        withRouting {
            handleRequest(HttpMethod.Get, "/api/v1/frequencies/UWLL").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(listOf(TestFrequencies.unicom, TestFrequencies.atis), Json.decodeFromString(response.content!!))
            }
        }
    }

    @Test
    fun `Incorrect ICAO request should return 400 status`() {
        withRouting {
            handleRequest(HttpMethod.Get, "/api/v1/frequencies/_UWLL").apply {
                assertEquals(HttpStatusCode.BadRequest, response.status())
                assertEquals("You should pass ICAO code instead of _UWLL", response.content)
            }
        }
    }

    @Test
    fun `Unknown ICAO request should return 404 status`() {
        withRouting {
            handleRequest(HttpMethod.Get, "/api/v1/frequencies/UUUU").apply {
                assertEquals(HttpStatusCode.NotFound, response.status())
                assertEquals("Frequencies of airport with ICAO UUUU not found", response.content)
            }
        }
    }
}