package com.example.data.network

import com.example.domain.models.RateListItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeApiClient {

    @GET("latest")
    suspend fun getRate(
        @Query("access_key") apiKey: String,
        @Query("symbols") currency: String
    ): Response<RateListItem>

}