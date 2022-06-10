package com.danp.danp_laboratorio05

import androidx.annotation.WorkerThread
import com.danp.danp_laboratorio05.data.Car
import com.danp.danp_laboratorio05.data.CarDao
import kotlinx.coroutines.flow.Flow

class CarRepository(private val carDao: CarDao) {
    val allItems: Flow<List<Car>> = carDao.getAllItems()

    fun getItem(id: Int): Flow<Car> {
        return carDao.getItem(id)
    }

    @Suppress("RedundantSuppressModifier")
    @WorkerThread
    suspend fun insertItem(car: Car) {
        carDao.insert(car)
    }

    @Suppress("RedundantSuppressModifier")
    @WorkerThread
    suspend fun addItem(car: Car) {
        carDao.insert(car)
    }

    @Suppress("RedundantSuppressModifier")
    @WorkerThread
    suspend fun updateItem(car: Car) {
        carDao.update(car)

    }

    @Suppress("RedundantSuppressModifier")
    @WorkerThread
    suspend fun deleteItem(car: Car) {
        carDao.delete(car)
    }
}