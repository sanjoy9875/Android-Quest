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
class EventDetailsViewModel @Inject constructor(
    private val repository: EventRepository
) : ViewModel() {
    var event by mutableStateOf<Event?>(null)
        private set

    fun loadEvent(id: Int) {
        viewModelScope.launch {
            event = repository.getEvent(id)
        }
    }
}
