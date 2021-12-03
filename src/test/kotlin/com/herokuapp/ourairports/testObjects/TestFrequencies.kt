package com.herokuapp.ourairports.testObjects

import com.herokuapp.ourairports.repository.model.Frequency
import com.herokuapp.ourairports.repository.model.enums.FrequencyType

object TestFrequencies {
    val unicom = Frequency(
        type = FrequencyType.UNICOM,
        description = "Standard UNICOM",
        valueMhz = 122.8f
    )
    val atis = Frequency(
        type = FrequencyType.WEATHER_OBSERVATION,
        description = "ATIS",
        valueMhz = 118.275f
    )
}