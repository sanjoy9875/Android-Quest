package com.example.dependencyinjection.Quest2.domain.model

data class Product(
    val id: Int,
    val title: String,
    val price: Float,
    val description: String,
    val category: String,
    val thumbnail: String
)