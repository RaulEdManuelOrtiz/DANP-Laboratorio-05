package com.danp.danp_laboratorio05

import android.app.Application
import com.danp.danp_laboratorio05.data.CarRoomDatabase

class CarApplication : Application() {
    private val database by lazy { CarRoomDatabase.getDatabase(this) }
    val repository by lazy { CarRepository(database.carDao()) }
}