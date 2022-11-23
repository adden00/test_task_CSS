package com.example.data.mappers

import com.example.data.database.entities.FavourRateItem
import com.example.domain.models.RateItem

internal fun RateItem.toEntity(): FavourRateItem {
    return FavourRateItem( name = this.name, rate = this.rate)
}

internal fun FavourRateItem.toDomain(): RateItem {
    return RateItem(name = this.name, rate = this.rate)
}