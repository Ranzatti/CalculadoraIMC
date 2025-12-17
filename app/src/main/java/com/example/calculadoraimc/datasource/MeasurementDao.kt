package com.example.calculadoraimc.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MeasurementDao {
    @Query("SELECT * FROM measurements ORDER BY date DESC")
    fun getAllMeasurements(): Flow<List<Measurement>>

    @Insert
    suspend fun insert(measurement: Measurement)

    @Query("SELECT * FROM measurements WHERE id = :id")
    suspend fun getById(id: Int): Measurement?
}