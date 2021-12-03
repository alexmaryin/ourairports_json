package com.herokuapp.ourairports.features

import com.herokuapp.ourairports.plugins.configureRouting
import com.herokuapp.ourairports.plugins.configureSerialization
import io.ktor.server.testing.*
import org.junit.After
import org.junit.Before
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest

open class FeatureKoinTest(private val withDb: Boolean = false) : KoinTest {

    @Before
    fun setUp() {
        startKoin {
            modules(testModule(withDb))
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    fun withRouting(test: TestApplicationEngine.() -> Unit) {
        withTestApplication ({
            configureSerialization()
            configureRouting()
        }) {
            test(this)
        }
    }
}