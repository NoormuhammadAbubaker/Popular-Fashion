package com.example.popularfashion.models

data class ProductResponse(
    val _id: Int,
    val category: String,
    val description: String,
    val image: String,
    val isNew: Boolean,
    val oldPrice: String,
    val price: Double,
    val quantity: Int,
    val rating: Int,
    val title: String
)