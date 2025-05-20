package com.example.dependencyinjection.Quest2.data.repository

import com.example.dependencyinjection.Quest2.data.api.ProductApiService
import com.example.dependencyinjection.Quest2.data.db.ProductDao
import com.example.dependencyinjection.Quest2.data.mapper.toDomain
import com.example.dependencyinjection.Quest2.data.mapper.toEntity
import com.example.dependencyinjection.Quest2.domain.model.Product
import com.example.dependencyinjection.Quest2.domain.model.SearchParams
import com.example.dependencyinjection.Quest2.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: ProductApiService,
    private val dao: ProductDao
) : ProductRepository {

    override fun getProducts(params: SearchParams): Flow<List<Product>> =
        dao.getAll().map { list ->
            list.map { it.toDomain() }
                .filter { it.title.contains(params.query, true) }
                .filter { params.category == null || it.category == params.category }
                .sortedBy { if (params.sortAscending) it.price else -it.price }
        }

    override suspend fun refresh(params: SearchParams) {
        val result = api.searchProducts(params.query)
        dao.clear()
        dao.insertAll(result.products.map { it.toEntity() })
    }
}