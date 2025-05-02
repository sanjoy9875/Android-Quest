package com.example.dependencyinjection.Quest2.data.api

import com.example.dependencyinjection.Quest2.data.model.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductApiService {
    @GET("products/search")
    suspend fun searchProducts(@Query("q") query: String): ProductResponse
}