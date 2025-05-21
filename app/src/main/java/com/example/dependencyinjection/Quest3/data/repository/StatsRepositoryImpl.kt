package com.example.dependencyinjection.Quest3.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.dependencyinjection.Quest3.domain.model.Order
import com.example.dependencyinjection.Quest3.domain.model.OrderItem
import com.example.dependencyinjection.Quest3.domain.model.User
import com.example.dependencyinjection.Quest3.domain.repository.StatsRepository
import java.time.LocalDateTime
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
class StatsRepositoryImpl @Inject constructor() : StatsRepository {

    // 3 fixed users
    private val users = listOf(
        User(userId = "1", username = "Alice"),
        User(userId = "2", username = "Bob"),
        User(userId = "3", username = "Charlie")
    )

    // 3 fixed orders
    private val orders = listOf(
        Order(
            orderId = "order1",
            userId = "1",
            items = listOf(
                OrderItem(productId = "p1", quantity = 2, pricePerUnit = 10.0),
                OrderItem(productId = "p2", quantity = 1, pricePerUnit = 20.0)
            ),
            timestamp = LocalDateTime.of(2024, 1, 1, 0, 0)
        ),
        Order(
            orderId = "order2",
            userId = "2",
            items = listOf(
                OrderItem(productId = "p1", quantity = 3, pricePerUnit = 10.0)
            ),
            timestamp = LocalDateTime.of(2024, 1, 2, 0, 0)
        ),
        Order(
            orderId = "order3",
            userId = "1",
            items = listOf(
                OrderItem(productId = "p3", quantity = 1, pricePerUnit = 50.0)
            ),
            timestamp = LocalDateTime.of(2024, 1, 3, 0, 0)
        )
    )

    override fun getUsers(): List<User> = users
    override fun getOrders(): List<Order> = orders

    // Total revenue = sum of quantity * pricePerUnit for all items in all orders
    override fun calculateTotalRevenue(): Double =
        orders.fold(0.0) { acc, order ->
            acc + order.items.fold(0.0) { sum, item ->
                sum + item.quantity * item.pricePerUnit
            }
        }

    // Most expensive item = one with highest quantity * pricePerUnit
    override fun findMostExpensiveOrderItem(): OrderItem =
        orders.flatMap { it.items }
            .reduce { acc, item ->
                val accTotal = acc.quantity * acc.pricePerUnit
                val itemTotal = item.quantity * item.pricePerUnit
                if (itemTotal > accTotal) item else acc
            }

    // Unique product IDs used in all orders
    override fun getAllUniqueProductIds(): Set<String> =
        orders.flatMap { it.items }.map { it.productId }.toSet()

    // For each productId, count total quantity sold
    override fun getProductSalesCount(): Map<String, Int> =
        orders.flatMap { it.items }
            .groupingBy { it.productId }
            .fold(0) { acc, item -> acc + item.quantity }

    // How much each user has spent in total
    override fun summariseUserSpending(): Map<String, Double> {
        val userMap = users.associateBy { it.userId } // maps "1" -> Alice, etc.
        return orders.groupBy { it.userId } // groups all orders by userId
            .mapValues { entry ->
                entry.value.sumOf { order ->
                    order.items.sumOf { it.quantity * it.pricePerUnit }
                }
            }
            .mapKeys { userMap[it.key]?.username ?: "Unknown" }
    }

    // Track loyalty points gained by a user over time
    override fun trackUserLoyaltyPoints(user: User, initialPoints: Int): List<Int> {
        val userOrders = orders.filter { it.userId == user.userId }

        return userOrders.map { order ->
            order.items.sumOf { it.quantity * it.pricePerUnit }
        }.scan(initialPoints) { acc, orderTotal ->
            acc + orderTotal.toInt() // converting total price to points
        }
    }
}


