package com.example.dependencyinjection.Quest2.domain.repository

import com.example.dependencyinjection.Quest2.domain.model.Product
import com.example.dependencyinjection.Quest2.domain.model.SearchParams
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProducts(params: SearchParams): Flow<List<Product>>
    suspend fun refresh(params: SearchParams)
}