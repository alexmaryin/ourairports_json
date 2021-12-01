package com.herokuapp.ourairports.repository.model

import com.herokuapp.ourairports.repository.model.enums.RunwaySurface
import com.herokuapp.ourairports.repository.model.serializers.RunwaySurfaceSerializer
import kotlinx.serialization.Serializable

@Serializable
data class Runway(
    val lengthFeet: Int? = null,
    val widthFeet: Int? = null,
    @Serializable(with = RunwaySurfaceSerializer::class) val surface: RunwaySurface,
    val closed: Boolean,
    val lowNumber: String,
    val lowElevationFeet: Int? = null,
    val lowHeading: Int,
    val highNumber: String,
    val highElevationFeet: Int? = null,
    val highHeading: Int,
)
