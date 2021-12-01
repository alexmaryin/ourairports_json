package com.herokuapp.ourairports.data.model

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

object FrequenciesTable : IntIdTable() {
    val airportId: Column<Int> = integer("airport_ref")
    val type: Column<String> = varchar("freq_type", 50)
    val description: Column<String?> = varchar("description", 100).nullable()
    val valueMhz: Column<Float> = float("frequency_mhz")
}
