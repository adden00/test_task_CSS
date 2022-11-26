package com.example.domain.usecases

import com.example.domain.repository.RateRepository

class DeleteAllFavoursUseCase(private val repository: RateRepository) {
    suspend operator fun invoke() {
        repository.deleteAllFromFavour()
    }
}