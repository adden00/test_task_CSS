package com.example.domain.repository

import com.example.domain.models.RateItem
import com.example.domain.models.RateListItem
import kotlinx.coroutines.flow.Flow

interface RateRepository {
    suspend fun getRate(currencies: String): RateListItem?
    suspend fun insertFavourItem(item: RateItem)
    suspend fun getFavourItems(): Flow<List<RateItem>>
    suspend fun deleteFromFavour(item: RateItem)
    suspend fun deleteAllFromFavour()
}