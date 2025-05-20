package com.example.dependencyinjection.Quest3.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.dependencyinjection.Quest3.domain.model.Order
import com.example.dependencyinjection.Quest3.domain.model.OrderItem
import com.example.dependencyinjection.Quest3.domain.model.User
import com.example.dependencyinjection.Quest3.domain.repository.StatsRepository
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
class StatsRepositoryImpl @Inject constructor() : StatsRepository {

    private val users = List(10) {
        User(
            userId = UUID.randomUUID().toString(),
            username = "user$it"
        )
    }

    private val orders = List(50) {
        val user = users.random()
        Order(
            orderId = UUID.randomUUID().toString(),
            userId = user.userId,
            items = List((1..5).random()) {
                OrderItem(
                    productId = "product${(1..20).random()}",
                    quantity = (1..10).random(),
                    pricePerUnit = BigDecimal((10..100).random())
                )
            },
            timestamp = LocalDateTime.now().minusDays((0..30).random().toLong())
        )
    }

    override fun getUsers(): List<User> = users
    override fun getOrders(): List<Order> = orders

    override fun calculateTotalRevenue(): BigDecimal =
        orders.fold(BigDecimal.ZERO) { acc, order ->
            acc + order.items.fold(BigDecimal.ZERO) { itemAcc, item ->
                itemAcc + item.totalPrice
            }
        }

    override fun findMostExpensiveOrderItem(): OrderItem =
        orders.flatMap { it.items }.reduce { acc, item ->
            if (item.totalPrice > acc.totalPrice) item else acc
        }

    override fun getAllUniqueProductIds(): Set<String> =
        orders.flatMap { it.items }.map { it.productId }.toSet()

    override fun getProductSalesCount(): Map<String, Int> =
        orders.flatMap { it.items }
            .groupingBy { it.productId }
            .fold(0) { acc, item -> acc + item.quantity }

    override fun summarizeUserSpending(): Map<String, Double> {
        val userMap = users.associateBy { it.userId }
        return orders.groupBy { it.userId }
            .mapNotNull { (userId, ordersForUser) ->
                val username = userMap[userId]?.username ?: return@mapNotNull null
                val totalSpent = ordersForUser.sumOf { order ->
                    order.items.sumOf { it.totalPrice }.toDouble()
                }
                username to totalSpent
            }.toMap()
    }

    override fun trackUserLoyaltyPoints(user: User, initialPoints: Int): List<Int> {
        val userOrders = orders.filter { it.userId == user.userId }.sortedBy { it.timestamp }
        return userOrders.scan(initialPoints) { acc, order ->
            val pointsEarned = order.items.sumOf { it.totalPrice.toInt() }
            acc + pointsEarned
        }
    }
}
