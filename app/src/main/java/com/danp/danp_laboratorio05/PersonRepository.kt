package com.danp.danp_laboratorio05

import androidx.annotation.WorkerThread
import com.danp.danp_laboratorio05.data.Person
import com.danp.danp_laboratorio05.data.PersonDao
import kotlinx.coroutines.flow.Flow

class PersonRepository(private val personDao: PersonDao) {
    val allItems: Flow<List<Person>> = personDao.getAllItems()

    fun getItem(id: Int): Flow<Person> {
        return personDao.getItem(id)
    }

    @Suppress("RedundantSuppressModifier")
    @WorkerThread
    suspend fun insertItem(person: Person) {
        personDao.insert(person)
    }

    @Suppress("RedundantSuppressModifier")
    @WorkerThread
    suspend fun addItem(person: Person) {
        personDao.insert(person)
    }

    @Suppress("RedundantSuppressModifier")
    @WorkerThread
    suspend fun updateItem(person: Person) {
        personDao.update(person)

    }

    @Suppress("RedundantSuppressModifier")
    @WorkerThread
    suspend fun deleteItem(person: Person) {
        personDao.delete(person)
    }
}