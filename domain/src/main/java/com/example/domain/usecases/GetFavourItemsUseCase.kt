package com.example.domain.usecases

import com.example.domain.models.RateItem
import com.example.domain.repository.RateRepository

class GetFavourItemsUseCase (private val repository: RateRepository) {
    suspend operator fun invoke(): List<RateItem> {
        return repository.getFavourItems()
    }
}