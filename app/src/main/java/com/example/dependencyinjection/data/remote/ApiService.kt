package com.example.dependencyinjection.data.remote

import com.example.dependencyinjection.data.model.Event
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("posts")
    suspend fun getEvents(): List<Event>

    @GET("posts/{id}")
    suspend fun getEvent(@Path("id") id: Int): Event
}