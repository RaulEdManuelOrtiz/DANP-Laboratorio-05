package com.danp.danp_laboratorio05.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Car::class], version = 1, exportSchema = false)
public abstract class CarRoomDatabase : RoomDatabase() {

    abstract fun carDao(): CarDao

    companion object {

        @Volatile
        private var INSTANCE: CarRoomDatabase? = null

        fun getDatabase(context: Context): CarRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CarRoomDatabase::class.java,
                    "item_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}