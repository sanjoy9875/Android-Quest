package com.example.dependencyinjection.Quest3.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsScreen(viewModel: StatsViewModel = hiltViewModel()) {
    val titleColor = MaterialTheme.colorScheme.primary
    val labelColor = MaterialTheme.colorScheme.onSurfaceVariant
    val valueColor = MaterialTheme.colorScheme.primary

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "E-commerce Statistics",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = titleColor
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = titleColor
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                StatisticItem(
                    label = "Total Revenue",
                    // Use String.format for 2 decimal places
                    value = "$${String.format("%.2f", viewModel.totalRevenue)}",
                    labelColor = labelColor,
                    valueColor = valueColor
                )
            }

            item {
                StatisticItem(
                    label = "Most Expensive Item",
                    value = "${viewModel.mostExpensiveItem.productId} - $${String.format("%.2f", viewModel.mostExpensiveItem.pricePerUnit * viewModel.mostExpensiveItem.quantity)}",
                    labelColor = labelColor,
                    valueColor = valueColor
                )
            }

            item {
                StatisticItem(
                    label = "Unique Product IDs",
                    value = viewModel.uniqueProductIds.joinToString(", "),
                    labelColor = labelColor,
                    valueColor = valueColor
                )
            }

            item {
                StatisticItem(
                    label = "Product Sales",
                    value = viewModel.productSales.entries.joinToString("\n") { "${it.key}: ${it.value}" },
                    labelColor = labelColor,
                    valueColor = valueColor
                )
            }

            item {
                StatisticItem(
                    label = "User Spending",
                    value = viewModel.userSpending.entries.joinToString("\n") {
                        "${it.key}: $${String.format("%.2f", it.value)}"
                    },
                    labelColor = labelColor,
                    valueColor = valueColor
                )
            }

            item {
                StatisticItem(
                    label = "Loyalty Points History",
                    value = viewModel.loyaltyPoints.joinToString(" → "),
                    labelColor = labelColor,
                    valueColor = valueColor
                )
            }
        }
    }
}

@Composable
fun StatisticItem(
    label: String,
    value: String,
    labelColor: Color,
    valueColor: Color
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = labelColor
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = value,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color = valueColor,
            lineHeight = 20.sp
        )
    }
}


