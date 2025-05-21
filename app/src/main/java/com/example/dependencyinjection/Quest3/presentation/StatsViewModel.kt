package com.example.dependencyinjection.Quest3.presentation

import androidx.lifecycle.ViewModel
import com.example.dependencyinjection.Quest3.domain.repository.StatsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val repository: StatsRepository
) : ViewModel() {
    val totalRevenue = repository.calculateTotalRevenue()
    val mostExpensiveItem = repository.findMostExpensiveOrderItem()
    val uniqueProductIds = repository.getAllUniqueProductIds()
    val productSales = repository.getProductSalesCount()
    val userSpending = repository.summariseUserSpending()
    val loyaltyPoints = repository.trackUserLoyaltyPoints(
        repository.getUsers().first(),
        initialPoints = 0
    )
}
