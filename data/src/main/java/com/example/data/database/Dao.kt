package com.example.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.data.database.entities.FavourRateItem

@Dao
interface Dao {

    @Insert (onConflict = REPLACE)
    suspend fun insertFavourItem(item: FavourRateItem)

    @Query ("SELECT * FROM FavourRateItem")
    suspend fun getFavouriteRates(): List<FavourRateItem>

    @Query ("DELETE FROM FavourRateItem WHERE name IS :name")
    suspend fun deleteFavourItem(name: String)

}