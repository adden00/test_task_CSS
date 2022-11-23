package com.example.data.repository

import com.example.data.database.Dao
import com.example.data.mappers.toDomain
import com.example.data.mappers.toEntity
import com.example.data.network.NetworkService
import com.example.domain.models.RateItem
import com.example.domain.models.RateListItem
import com.example.domain.repository.RateRepository

class GetRateRepositoryImpl(private val apiService: NetworkService, private val dao: Dao): RateRepository {
    override suspend fun getRate(currencies: String): RateListItem? {
        return apiService.getRate(currencies)
    }

    override suspend fun insertFavourItem(item: RateItem) {
        dao.insertFavourItem(item.toEntity())
    }

    override suspend fun getFavourItems(): List<RateItem> {
        return dao.getFavouriteRates().map { it.toDomain() }
    }

    override suspend fun deleteFromFavour(item: RateItem) {
        dao.deleteFavourItem(item.name)
    }

}