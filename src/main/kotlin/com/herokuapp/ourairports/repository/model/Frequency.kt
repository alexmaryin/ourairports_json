package com.herokuapp.ourairports.repository.model

import com.herokuapp.ourairports.repository.model.enums.FrequencyType
import com.herokuapp.ourairports.repository.model.serializers.FrequencyTypeSerializer
import kotlinx.serialization.Serializable

@Serializable
data class Frequency(
    @Serializable(with = FrequencyTypeSerializer::class) val type: FrequencyType,
    val description: String? = null,
    val valueMhz: Float
)
