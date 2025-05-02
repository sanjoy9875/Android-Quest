package com.example.dependencyinjection.Quest2.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProductEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val price: Float,
    val description: String,
    val category: String,
    val thumbnail: String
)