package com.example.popularfashion.di

import com.example.popularfashion.api.FashionApi
import com.example.popularfashion.utils.Constant
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {
    private val retrofit by lazy {
        Retrofit.Builder().baseUrl(Constant.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
    }






     val apiInterface by lazy {
        retrofit.create(FashionApi::class.java)
    }
}