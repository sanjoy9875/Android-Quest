package com.example.dependencyinjection.data.repository

import com.example.dependencyinjection.data.model.Event
import com.example.dependencyinjection.data.remote.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : EventRepository {
    override suspend fun getEvents(): List<Event> = apiService.getEvents()
    override suspend fun getEvent(id: Int): Event = apiService.getEvent(id)
}
