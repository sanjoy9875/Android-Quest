package com.example.dependencyinjection.Quest2.data.model

data class ProductResponse(
    val products: List<ProductDto>
)

data class ProductDto(
    val id: Int,
    val title: String,
    val price: Float,
    val description: String,
    val category: String,
    val thumbnail: String
)