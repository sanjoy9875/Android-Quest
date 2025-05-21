package com.example.dependencyinjection.Quest3.domain.repository

import com.example.dependencyinjection.Quest3.domain.model.Order
import com.example.dependencyinjection.Quest3.domain.model.OrderItem
import com.example.dependencyinjection.Quest3.domain.model.User
import java.math.BigDecimal

interface StatsRepository {
    fun getUsers(): List<User>
    fun getOrders(): List<Order>

    fun calculateTotalRevenue(): Double
    fun findMostExpensiveOrderItem(): OrderItem
    fun getAllUniqueProductIds(): Set<String>
    fun getProductSalesCount(): Map<String, Int>
    fun summariseUserSpending(): Map<String, Double>
    fun trackUserLoyaltyPoints(user: User, initialPoints: Int): List<Int>
}