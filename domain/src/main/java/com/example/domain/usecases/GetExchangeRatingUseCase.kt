package com.example.domain.usecases

import com.example.domain.models.RateItem
import com.example.domain.models.RateListItem
import com.example.domain.repository.RateRepository
import kotlin.math.roundToInt

class GetExchangeRatingUseCase(private val repository: RateRepository) {

    suspend operator fun invoke(currencies: String): List<RateItem> {
        val rate = repository.getRate(currencies)
        val resultList = mutableListOf<RateItem>()
        if (rate != null) {
            for (it in rate.rates.keys) {
                resultList.add(RateItem(it, (rate.rates[it]!!*100.0).roundToInt()/100.0))
            }
        }
        return resultList
    }

}