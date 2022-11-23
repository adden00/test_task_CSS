package com.example.testtaskcss.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.RateItem
import com.example.domain.usecases.DeleteFavourItemUseCase
import com.example.domain.usecases.GetExchangeRatingUseCase
import com.example.domain.usecases.GetFavourItemsUseCase
import com.example.domain.usecases.InsertFavourItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getExchangeUseCase: GetExchangeRatingUseCase,
    private val getFavourItemsUseCase: GetFavourItemsUseCase,
    private val insertFavourItemUseCase: InsertFavourItemUseCase,
    private val deleteFavourItemUseCase: DeleteFavourItemUseCase
) :
    ViewModel() {

    private val _rateList = MutableStateFlow(listOf<RateItem>())
    val rateList: StateFlow<List<RateItem>> = _rateList.asStateFlow()

    private val _favourRateList = MutableStateFlow(listOf<RateItem>())
    val favourRateList: StateFlow<List<RateItem>> = _favourRateList.asStateFlow()

    private val _popularRateList = MutableStateFlow(listOf<RateItem>())
    val popularRateList: StateFlow<List<RateItem>> = _popularRateList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            loadFavoursFromDb()
            loadPopularRate()
        }
    }

    fun loadRate(currencies: String) {
        _isLoading.value = true
        val curList = _rateList.value.toMutableList()
        viewModelScope.launch {
            val result = getExchangeUseCase(currencies)
            val temp = result.toMutableList()
            result.forEach {
                if (it in _rateList.value)
                    temp.remove(it)
            }
            _rateList.value = curList + temp
            _isLoading.value = false
        }
    }

    fun loadPopularRate() {
        val popularRates = "USD,EUR,RUB,GEL,KZT,GBP"
        _isLoading.value = true
        viewModelScope.launch {
            val result = getExchangeUseCase(popularRates)
            Log.d("MyLog", result.toString())
            _popularRateList.value = result
            _isLoading.value = false
        }
    }


    fun insetRateToFavour(item: RateItem) {
        viewModelScope.launch {
            insertFavourItemUseCase(item)
            loadFavoursFromDb()
        }
    }

    fun updateFavouriteRate() {
        _isLoading.value = false
        var response = ""
        _favourRateList.value.forEach {
            response += "${it.name},"
        }
        if (response != "") {
            Log.d("MyLog", "response: $response")
            response = response.substring(0, response.length - 1)
            _isLoading.value = true

            viewModelScope.launch {
                val result = getExchangeUseCase(response)
                _favourRateList.value = result
                _isLoading.value = false
            }
        }
    }

    fun deleteRateFromFavour(item: RateItem) {
        viewModelScope.launch {
            deleteFavourItemUseCase(item)
            loadFavoursFromDb()
        }
    }

    private suspend fun loadFavoursFromDb() {
        val result = getFavourItemsUseCase()
        _favourRateList.value = result
    }
}