package com.example.dependencyinjection.Quest1.data.repository

import com.example.dependencyinjection.Quest1.data.model.Event
import com.example.dependencyinjection.Quest1.data.remote.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : EventRepository {
    override suspend fun getEvents(): List<Event> = apiService.getEvents()
    override suspend fun getEvent(id: Int): Event = apiService.getEvent(id)
}
