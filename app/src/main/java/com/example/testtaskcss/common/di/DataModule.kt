package com.example.testtaskcss.common.di

import android.content.Context
import androidx.room.Room
import com.example.data.database.Dao
import com.example.data.database.FavourDataBase
import com.example.data.network.ExchangeApiClient
import com.example.data.network.NetworkService
import com.example.data.repository.GetRateRepositoryImpl
import com.example.domain.repository.RateRepository
import com.example.testtaskcss.common.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideApiClient(): ExchangeApiClient {
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL).build().create(ExchangeApiClient::class.java)
    }

    @Provides
    @Singleton
    fun provideNetworkService(apiClient: ExchangeApiClient) = NetworkService(apiClient)

    @Provides
    @Singleton
    fun provideFavourDatabase(@ApplicationContext context: Context): FavourDataBase {
        return Room.databaseBuilder(context, FavourDataBase::class.java, "favour_database").build()
    }

    @Provides
    @Singleton
    fun provideDao(db: FavourDataBase) = db.getDao()

    @Provides
    @Singleton
    fun provideRepository(service: NetworkService, dao: Dao): RateRepository {
        return GetRateRepositoryImpl(service, dao)
    }
}