package com.example.data.network

import android.util.Log
import com.example.domain.models.RateListItem
import java.lang.Exception

private const val API_KEY = "93431f0a1bae151b353302898ffc3f0a"

class NetworkService(private val api: ExchangeApiClient) {

    suspend fun getRate(currencies: String): RateListItem? {
        var result: RateListItem? = null
        try {
            result = api.getRate(API_KEY, currencies).body()
        }
        catch (e: Exception) {
            Log.d("MyLog", e.toString())
        }
        return result
    }
}