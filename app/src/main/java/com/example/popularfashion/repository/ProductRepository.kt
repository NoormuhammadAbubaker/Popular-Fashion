package com.example.popularfashion.repository

import androidx.lifecycle.MutableLiveData
import com.example.popularfashion.api.FashionApi
import com.example.popularfashion.models.ProductResponse
import com.example.popularfashion.utils.NetworkResult
import org.json.JSONObject

class ProductRepository(private val fashionApi: FashionApi ) {
    private val _statusLiveData = MutableLiveData<NetworkResult<Pair<Boolean, String>>>()
    val statusLiveData get() = _statusLiveData

    private val _fashionLiveData = MutableLiveData<NetworkResult<List<ProductResponse>>>()
    val fashionLiveData get() = _fashionLiveData
    private val _itemDetailLiveData = MutableLiveData<NetworkResult<List<ProductResponse>>>()
    val itemDetailLiveData get() = _itemDetailLiveData

    suspend fun getListOfProduct() {
        _fashionLiveData.postValue(NetworkResult.Loading())
        val response = fashionApi.getProductList()
        if (response.isSuccessful && response.body() != null) {
            _fashionLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _fashionLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _fashionLiveData.postValue(NetworkResult.Error("Something Went Wrong"))
        }
    }
    suspend fun getSingleProduct(index:Int) {
        _itemDetailLiveData.postValue(NetworkResult.Loading())
        val response = fashionApi.getProductDetail(index)
        if (response.isSuccessful && response.body() != null) {
            _itemDetailLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _itemDetailLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _itemDetailLiveData.postValue(NetworkResult.Error("Something Went Wrong"))
        }
    }
}