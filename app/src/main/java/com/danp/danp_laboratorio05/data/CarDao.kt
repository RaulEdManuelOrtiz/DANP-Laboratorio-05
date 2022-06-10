package com.danp.danp_laboratorio05.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CarDao {
    @Query("SELECT * FROM car_table ORDER BY plate ASC")
    fun getAllItems(): Flow<List<Car>>

    @Query("SELECT * FROM car_table WHERE id = :id")
    fun getItem(id: Int): Flow<Car>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Car)

    @Update
    suspend fun update(item: Car)

    @Delete
    suspend fun delete(item: Car)
}