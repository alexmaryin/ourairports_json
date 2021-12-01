package com.herokuapp.ourairports.data.converters

interface ConverterFactory {
    fun convert(filename: String): Boolean
}