package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.database.entities.FavourRateItem

@Database (entities = [FavourRateItem::class], version = 1)

abstract class FavourDataBase: RoomDatabase() {
    abstract fun getDao(): Dao
}