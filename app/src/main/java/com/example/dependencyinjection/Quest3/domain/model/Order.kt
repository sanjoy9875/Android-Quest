package com.example.dependencyinjection.Quest3.domain.model

import java.time.LocalDateTime

data class Order(
    val orderId: String,
    val userId: String,
    val items: List<OrderItem>,
    val timestamp: LocalDateTime
)
