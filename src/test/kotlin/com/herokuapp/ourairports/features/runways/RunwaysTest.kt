package com.herokuapp.ourairports.features.runways

import com.herokuapp.ourairports.plugins.configureRouting
import com.herokuapp.ourairports.plugins.configureSerialization
import com.herokuapp.ourairports.repository.RunwaysRepository
import com.herokuapp.ourairports.testObjects.TestRunwayRepository
import com.herokuapp.ourairports.testObjects.TestRunways
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import kotlin.test.assertEquals

class RunwaysTest : KoinTest {

    @Before
    fun setUp() {
        startKoin {
            modules(
                module {
                    single<RunwaysRepository> { TestRunwayRepository() }
                }
            )
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    private fun withRouting(test: TestApplicationEngine.() -> Unit) {
        withTestApplication ({
            configureSerialization()
            configureRouting()
        }) {
            test(this)
        }
    }

    @Test
    fun `Correct request should return 200 status and runways json array`() {
        withRouting {
            handleRequest(HttpMethod.Get, "/api/v1/runways/UWLL").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(TestRunways.UWLL, Json.decodeFromString(response.content!!))
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