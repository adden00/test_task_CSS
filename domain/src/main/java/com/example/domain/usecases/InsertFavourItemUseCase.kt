package com.example.domain.usecases

import com.example.domain.models.RateItem
import com.example.domain.repository.RateRepository

class InsertFavourItemUseCase(private val repository: RateRepository) {
    suspend operator fun invoke(item: RateItem) {
        repository.insertFavourItem(item)
    }
}