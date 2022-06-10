package com.danp.danp_laboratorio05

import androidx.lifecycle.*
import com.danp.danp_laboratorio05.data.Person
import kotlinx.coroutines.launch

class PersonViewModel(private val repository: PersonRepository) : ViewModel() {
    val allItems: LiveData<List<Person>> = repository.allItems.asLiveData()

    fun getItem(id: Int): LiveData<Person> = repository.getItem(id).asLiveData()

    fun addItem(item: Person) = viewModelScope.launch {
        repository.insertItem(item)
    }

    fun updateItem(item: Person) = viewModelScope.launch {
        repository.updateItem(item)
    }

    fun deleteItem(item: Person) = viewModelScope.launch {
        repository.deleteItem(item)
    }

    fun isItemValid(name: String, surname: String, identification: String, phoneNumber: String): Boolean {
        if (name.trim().isBlank()
            || surname.trim().isBlank()
            || identification.trim().isBlank()
            || phoneNumber.trim().isBlank()
        ) {
            return false
        }
        return true
    }
}

class PersonViewModelFactory(private val repository: PersonRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PersonViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PersonViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}