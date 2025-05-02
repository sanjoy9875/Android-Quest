package com.example.dependencyinjection.Quest2.domain.model

data class SearchParams(
    val query: String = "",
    val category: String? = null,
    val sortAscending: Boolean = true
)