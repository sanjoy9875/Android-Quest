package com.example.dependencyinjection.data.repository

import com.example.dependencyinjection.data.model.Event

interface EventRepository {
    suspend fun getEvents(): List<Event>
    suspend fun getEvent(id: Int): Event
}