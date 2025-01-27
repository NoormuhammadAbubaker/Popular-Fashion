package com.example.popularfashion.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.popularfashion.models.ProductResponse
import com.example.popularfashion.repository.ProductRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: ProductRepository) : ViewModel() {


    val fashionLiveData get() = repository.fashionLiveData
    val detailLiveData get() = repository.itemDetailLiveData


    fun getAllProduct() {
        viewModelScope.launch {

            repository.getListOfProduct()
        }
    }

    fun getSingleProduct(index:Int) {
        viewModelScope.launch {
            repository.getSingleProduct(index)
        }
    }

}