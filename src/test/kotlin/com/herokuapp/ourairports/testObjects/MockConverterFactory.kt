package com.herokuapp.ourairports.testObjects

import com.herokuapp.ourairports.data.converters.ConverterFactory

object MockConverterFactory : ConverterFactory {
    override fun convert(filename: String): Boolean = true
}