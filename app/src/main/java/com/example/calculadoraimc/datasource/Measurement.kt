package com.example.calculadoraimc.datasource

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "measurements")
data class Measurement(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: Long,
    val weight: Double,
    val height: Double,
    val age: Int,
    val imc: Double,
    val classification: String,
    val tmb: Double,
    val pesoIdeal: Double,
    val caloriasDiarias: Double
)