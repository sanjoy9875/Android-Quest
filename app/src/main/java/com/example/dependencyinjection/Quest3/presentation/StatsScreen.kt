package com.example.dependencyinjection.Quest3.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dependencyinjection.Quest3.domain.model.User


@Composable
fun StatsScreen(viewModel: StatsViewModel = hiltViewModel()) {
    var selectedUser by remember { mutableStateOf(viewModel.users.firstOrNull()) }

    val totalRevenue = viewModel.calculateTotalRevenue()
    val expensiveItem = viewModel.findMostExpensiveOrderItem()
    val uniqueProductIds = viewModel.getAllUniqueProductIds()
    val productSales = viewModel.getProductSalesCount()
    val userSpending = viewModel.summarizeUserSpending()
    val loyaltyPoints = selectedUser?.let { viewModel.trackUserLoyaltyPoints(it, 100) } ?: emptyList()

    Column(modifier = Modifier.padding(16.dp)) {
        LazyColumn(modifier = Modifier.weight(1f)) {
            item { Text("Total Revenue: $$totalRevenue") }
            item { Text("Most Expensive Item: ${expensiveItem.productId} costing ${expensiveItem.totalPrice}") }
            item { Text("Unique Products: ${uniqueProductIds.size}") }
            item {
                Text("Product Sales:")
                productSales.forEach { (id, count) ->
                    Text("  $id -> $count units")
                }
            }
            item {
                Text("User Spending:")
                userSpending.forEach { (username, amount) ->
                    Text("  $username -> $amount")
                }
            }
            item {
                Text("Loyalty Points for ${selectedUser?.username ?: "Unknown"}:")
                loyaltyPoints.forEachIndexed { index, points ->
                    Text("  Order #$index -> $points points")
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        DropdownMenuBox(selectedUser, viewModel.users) {
            selectedUser = it
        }
    }
}

@Composable
fun DropdownMenuBox(
    selectedUser: User?,
    userList: List<User>,
    onUserSelected: (User) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        Button(onClick = { expanded = true }) {
            Text(selectedUser?.username ?: "Select user")
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            userList.forEach { user ->
                DropdownMenuItem(text = { Text(user.username) }, onClick = {
                    expanded = false
                    onUserSelected(user)
                })
            }
        }
    }
}
