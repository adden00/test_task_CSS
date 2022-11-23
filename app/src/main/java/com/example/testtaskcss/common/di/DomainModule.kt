package com.example.testtaskcss.common.di

import com.example.domain.repository.RateRepository
import com.example.domain.usecases.DeleteFavourItemUseCase
import com.example.domain.usecases.GetExchangeRatingUseCase
import com.example.domain.usecases.GetFavourItemsUseCase
import com.example.domain.usecases.InsertFavourItemUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object DomainModule {
    @Provides
    fun provideGetRateUseCase(repository: RateRepository) = GetExchangeRatingUseCase(repository)

    @Provides
    fun provideDeleteUseCase(repository: RateRepository) = DeleteFavourItemUseCase(repository)

    @Provides
    fun provideGetFavourItemsUseCase(repository: RateRepository) = GetFavourItemsUseCase(repository)

    @Provides
    fun provideInsertUseCase(repository: RateRepository) = InsertFavourItemUseCase(repository)
}