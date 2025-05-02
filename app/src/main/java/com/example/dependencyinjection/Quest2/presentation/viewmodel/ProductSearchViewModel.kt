package com.example.dependencyinjection.Quest2.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dependencyinjection.Quest2.domain.model.Product
import com.example.dependencyinjection.Quest2.domain.model.SearchParams
import com.example.dependencyinjection.Quest2.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductSearchViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val query = MutableStateFlow("")

    private val category = MutableStateFlow<String?>(null)
    internal val _category: StateFlow<String?> = category

    private val ascending = MutableStateFlow(true)
    private val manualRefresh = MutableSharedFlow<Unit>(extraBufferCapacity = 1)

    @OptIn(FlowPreview::class)
    private val debouncedQuery = query.debounce(300)

    private val params = combine(debouncedQuery, category, ascending) { q, c, a ->
        SearchParams(q, c, a)
    }

    val products: StateFlow<List<Product>> = merge(
        params,
        manualRefresh.map {
            SearchParams(query.value, category.value, ascending.value)
        }
    )
        .flatMapLatest { params ->
            repository.getProducts(params)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        viewModelScope.launch {
            merge(
                params,
                manualRefresh.map {
                    SearchParams(query.value, category.value, ascending.value)
                }
            ).collectLatest { params ->
                repository.refresh(params)
            }
        }
    }

    fun onQueryChanged(newQuery: String) {
        category.value = null
        query.value = newQuery
    }
    fun onCategoryChanged(newCategory: String?) {
        query.value = ""
        category.value = if (category.value == newCategory) null else newCategory
    }
    fun onSortOrderChanged(asc: Boolean) { ascending.value = asc }
    fun onManualRefresh() { manualRefresh.tryEmit(Unit)  }
}