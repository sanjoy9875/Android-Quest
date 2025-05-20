package com.example.dependencyinjection.Quest3.domain.model

import java.math.BigDecimal

data class OrderItem(
    val productId: String,
    val quantity: Int,
    val pricePerUnit: BigDecimal,
) {
    val totalPrice: BigDecimal get() = pricePerUnit * quantity.toBigDecimal()
}

