package com.example.domain.usecases

import com.example.domain.models.RateItem
import com.example.domain.repository.RateRepository
import kotlinx.coroutines.flow.Flow

class GetFavourItemsUseCase (private val repository: RateRepository) {
    suspend operator fun invoke(): Flow<List<RateItem>> {
        return repository.getFavourItems()
    }
}