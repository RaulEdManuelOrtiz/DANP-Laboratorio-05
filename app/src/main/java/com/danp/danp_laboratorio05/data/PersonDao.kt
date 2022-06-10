package com.danp.danp_laboratorio05.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {
    @Query("SELECT * FROM person_table")
    fun getAllItems(): Flow<List<Person>>

    @Query("SELECT * FROM person_table WHERE id = :id")
    fun getItem(id: Int): Flow<Person>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Person)

    @Update
    suspend fun update(item: Person)

    @Delete
    suspend fun delete(item: Person)
}