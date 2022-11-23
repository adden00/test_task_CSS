package com.example.domain.models

data class RateListItem(
    val base: String,
    val date: String,
    val rates: Map<String, Double>,
    val success: Boolean,
    val timestamp: Int,
    val isLoading: Boolean = false
)