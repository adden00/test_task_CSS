package com.example.testtaskcss.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.RateItem
import com.example.domain.usecases.*
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
    private val deleteFavourItemUseCase: DeleteFavourItemUseCase,
    private val deleteAllFavoursUseCase: DeleteAllFavoursUseCase
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

    private val _filterIsShown = MutableStateFlow(false)
    val filterIsShown: StateFlow<Boolean> = _filterIsShown.asStateFlow()

    private var allItems = listOf<RateItem>()

    init {
        viewModelScope.launch {
            loadPopularRate()
            getFavourItemsUseCase().collect {
                _favourRateList.value = it
            }
        }
    }

    fun showHideFilter() {
        _filterIsShown.value = !_filterIsShown.value
    }

    fun loadAllRates() {
        _isLoading.value = true
        viewModelScope.launch {
            val result = getExchangeUseCase("")
            _rateList.value = result
            _isLoading.value = false
            allItems = result
        }
    }

    fun searchItem(filter: String) {
        val result = mutableListOf<RateItem>()
        allItems.forEach {
            if (it.name.lowercase().contains(filter.lowercase()))
                result.add(it)
        }
        _rateList.value = result
    }

    fun loadPopularRate() {
        val popularRates = "USD,EUR,RUB,GEL,KZT,GBP"
        _isLoading.value = true
        viewModelScope.launch {
            val result = getExchangeUseCase(popularRates)
            _popularRateList.value = result
            _isLoading.value = false
        }
    }

    fun insetRateToFavour(item: RateItem) {
        viewModelScope.launch {
            insertFavourItemUseCase(item)
        }
    }

    fun updateFavouriteRate() {

        var response = ""
        _favourRateList.value.forEach {
            response += "${it.name},"
        }
        if (response != "") {
            response = response.substring(0, response.length - 1)
            _isLoading.value = true
            clearFavourite()
            viewModelScope.launch {

                val result = getExchangeUseCase(response)
                result.forEach {
                    insertFavourItemUseCase(it)
                }
                _isLoading.value = false
            }
        }
        else
            _isLoading.value = true
            _isLoading.value = false
    }

    fun clearFavourite() {
        viewModelScope.launch {
            deleteAllFavoursUseCase()
        }
    }

    fun deleteRateFromFavour(item: RateItem) {
        viewModelScope.launch {
            deleteFavourItemUseCase(item)
        }
    }

    fun sortFavour(sortType: String) {
        when (sortType) {
            "name up" -> {
                _favourRateList.value = _favourRateList.value.sortedBy { it.name }
            }
            "name down" -> {
                _favourRateList.value = _favourRateList.value.sortedByDescending { it.name }
            }

            "rate up" -> {
                _favourRateList.value = _favourRateList.value.sortedBy { it.rate }
            }

            "rate down" -> {
                _favourRateList.value = _favourRateList.value.sortedByDescending { it.rate }
            }
        }
    }
}