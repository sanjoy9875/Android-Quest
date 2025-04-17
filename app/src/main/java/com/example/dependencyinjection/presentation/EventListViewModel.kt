package com.example.dependencyinjection.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dependencyinjection.data.model.Event
import com.example.dependencyinjection.data.repository.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventListViewModel @Inject constructor(
    private val repository: EventRepository
) : ViewModel() {
    var eventList by mutableStateOf<List<Event>>(emptyList())
        private set

    init {
        viewModelScope.launch {
            eventList = repository.getEvents()
        }
    }
}