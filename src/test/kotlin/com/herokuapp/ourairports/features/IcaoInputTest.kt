package com.herokuapp.ourairports.features

import com.herokuapp.ourairports.features.utils.correctIcao
import org.junit.Test
import kotlin.test.assertEquals

class IcaoInputTest {

    @Test
    fun `correctIcao should return null if ICAO not starts with acceptable symbols`() {
        assertEquals(null, correctIcao("_UWLL"))
        assertEquals(null, correctIcao("*UWL"))
    }

    @Test
    fun `correctIcao should return null if ICAO contains unacceptable symbols`() {
        assertEquals(null, correctIcao("UWL_"))
        assertEquals(null, correctIcao("uW*&"))
    }

    @Test
    fun `correctIcao should return null if ICAO contains less than 4 symbols`() {
        assertEquals(null, correctIcao("UWL"))
        assertEquals(null, correctIcao("00A"))
    }

    @Test
    fun `correctIcao should return formatted ICAO input is correct`() {
        assertEquals("UWLL", correctIcao("uWll"))
        assertEquals("00AA", correctIcao("00Aa"))
    }

    @Test
    fun `correctIcao should return formatted ICAO with only 4 first symbols`() {
        assertEquals("UWLL", correctIcao("uWlllll"))
        assertEquals("00AA", correctIcao("00Aaaaa"))
    }
}