package com.example.dependencyinjection.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dependencyinjection.data.model.Event
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                val navController = rememberNavController()

                NavHost(navController, startDestination = "event_list") {
                    composable("event_list") {
                        val viewModel: EventListViewModel = hiltViewModel()
                        EventListScreen(viewModel.eventList) { eventId ->
                            navController.navigate("event_detail/$eventId")
                        }
                    }
                    composable("event_detail/{id}") { backStackEntry ->
                        val viewModel: EventDetailsViewModel = hiltViewModel()
                        val id = backStackEntry.arguments?.getString("id")?.toIntOrNull() ?: return@composable
                        LaunchedEffect(id) { viewModel.loadEvent(id) }
                        EventDetailsScreen(viewModel.event)
                    }
                }
            }
        }
    }
}

@Composable
fun EventListScreen(events: List<Event>, onItemClick: (Int) -> Unit) {
    LazyColumn(contentPadding = PaddingValues(10.dp)) {
        items(events) { event ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable { onItemClick(event.id) },
                elevation = CardDefaults.cardElevation()
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text(text = event.title, fontWeight = FontWeight.Bold)
                    Text(text = event.body, maxLines = 2, overflow = TextOverflow.Ellipsis)
                }
            }
        }
    }
}

@Composable
fun EventDetailsScreen(event: Event?) {
    if (event != null) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "Title: ${event.title}", style = MaterialTheme.typography.titleLarge)
            Text(text = "Description: ${event.body}")
        }
    } else {
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

