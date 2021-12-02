package com.herokuapp.ourairports.data.model

import com.herokuapp.ourairports.repository.model.Frequency
import com.herokuapp.ourairports.repository.model.enums.FrequencyType
import com.herokuapp.ourairports.repository.model.serializers.FrequencyTypeSerializer
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow

object FrequenciesTable : IntIdTable() {
    val airportId: Column<Int> = integer("airport_ref")
    val type: Column<String> = varchar("freq_type", 50)
    val description: Column<String?> = varchar("description", 100).nullable()
    val valueMhz: Column<Float> = float("frequency_mhz")
}

fun ResultRow.toFrequency() = Frequency(
    type = FrequencyTypeSerializer.getByString(this[FrequenciesTable.type]),
    description = this[FrequenciesTable.description],
    valueMhz = this[FrequenciesTable.valueMhz]
)