package com.example.popularfashion.api

import com.example.popularfashion.models.ProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FashionApi {

    @GET("smart")
    suspend fun getProductList() : Response<List<ProductResponse>>
    @GET("smart")
    suspend fun getProductDetail(@Query("id") id: Int) : Response<List<ProductResponse>>
 }