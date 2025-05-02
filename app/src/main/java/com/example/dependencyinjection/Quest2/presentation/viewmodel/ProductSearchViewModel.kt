package com.example.dependencyinjection.Quest2.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dependencyinjection.Quest2.domain.model.Product
import com.example.dependencyinjection.Quest2.domain.model.SearchParams
import com.example.dependencyinjection.Quest2.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductSearchViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val query = MutableStateFlow("")
    private val category = MutableStateFlow<String?>(null)
    private val ascending = MutableStateFlow(true)
    private val manualRefresh = MutableSharedFlow<Unit>()

    private val params = combine(query, category, ascending) { q, c, a ->
        SearchParams(q, c, a)
    }

    val products: StateFlow<List<Product>> = params
        .flatMapLatest { p ->
            repository.getProducts(p)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        viewModelScope.launch {
            combine(params, manualRefresh.onStart { emit(Unit) }) { p, _ -> p }
                .collectLatest { repository.refresh(it) }
        }
    }

    fun onQueryChanged(newQuery: String) {
        category.value = null
        query.value = newQuery
    }
    fun onCategoryChanged(newCategory: String?) {
        query.value = ""
        category.value = newCategory
    }
    fun onSortOrderChanged(asc: Boolean) { ascending.value = asc }
    fun onManualRefresh() { viewModelScope.launch { manualRefresh.emit(Unit) } }
}