package com.herokuapp.ourairports.testObjects

import com.herokuapp.ourairports.repository.model.Runway
import com.herokuapp.ourairports.repository.model.RunwaySurface

object TestRunways {
    val UWLL = listOf(
        Runway(
            lengthFeet = 10171,
            widthFeet = 148,
            surface = RunwaySurface.CONCRETE,
            closed = false,
            lowNumber = "02",
            lowElevationFeet = 449,
            lowHeading = 19,
            highNumber = "20",
            highElevationFeet = 377,
            highHeading = 199
        )
    )
}