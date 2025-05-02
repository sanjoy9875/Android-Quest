package com.example.dependencyinjection.Quest2.data.mapper

import com.example.dependencyinjection.Quest2.data.db.ProductEntity
import com.example.dependencyinjection.Quest2.data.model.ProductDto
import com.example.dependencyinjection.Quest2.domain.model.Product

fun ProductDto.toEntity() = ProductEntity(id, title, price, description, category, thumbnail)
fun ProductEntity.toDomain() = Product(id, title, price, description, category, thumbnail)