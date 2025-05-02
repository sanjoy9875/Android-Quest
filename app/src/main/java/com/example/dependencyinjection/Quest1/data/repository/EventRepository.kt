package com.example.dependencyinjection.Quest1.data.repository

import com.example.dependencyinjection.Quest1.data.model.Event

interface EventRepository {
    suspend fun getEvents(): List<Event>
    suspend fun getEvent(id: Int): Event
}