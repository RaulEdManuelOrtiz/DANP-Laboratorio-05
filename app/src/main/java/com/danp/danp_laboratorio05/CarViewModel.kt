package com.danp.danp_laboratorio05

import androidx.lifecycle.*
import com.danp.danp_laboratorio05.data.Car
import kotlinx.coroutines.launch

class CarViewModel(private val repository: CarRepository) : ViewModel() {
    val allItems: LiveData<List<Car>> = repository.allItems.asLiveData()

    fun getItem(id: Int): LiveData<Car> = repository.getItem(id).asLiveData()

    fun addItem(item: Car) = viewModelScope.launch {
        repository.insertItem(item)
    }

    fun updateItem(item: Car) = viewModelScope.launch {
        repository.updateItem(item)
    }

    fun deleteItem(item: Car) = viewModelScope.launch {
        repository.deleteItem(item)
    }

    fun isItemValid(carPlate: String, carColor: String, carBrand: String, carModel: String): Boolean {
        if (carPlate.trim().isBlank()
            || carColor.trim().isBlank()
            || carBrand.trim().isBlank()
            || carModel.trim().isBlank()
        ) {
            return false
        }
        return true
    }
}

class CarViewModelFactory(private val repository: CarRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CarViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CarViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}