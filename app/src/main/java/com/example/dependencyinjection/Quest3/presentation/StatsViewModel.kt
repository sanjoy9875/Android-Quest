package com.example.dependencyinjection.Quest3.presentation

import androidx.lifecycle.ViewModel
import com.example.dependencyinjection.Quest3.domain.model.User
import com.example.dependencyinjection.Quest3.domain.repository.StatsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val repository: StatsRepository
) : ViewModel() {
    val users = repository.getUsers()

    fun calculateTotalRevenue() = repository.calculateTotalRevenue()
    fun findMostExpensiveOrderItem() = repository.findMostExpensiveOrderItem()
    fun getAllUniqueProductIds() = repository.getAllUniqueProductIds()
    fun getProductSalesCount() = repository.getProductSalesCount()
    fun summarizeUserSpending() = repository.summarizeUserSpending()
    fun trackUserLoyaltyPoints(user: User, initialPoints: Int) = repository.trackUserLoyaltyPoints(user, initialPoints)
}
