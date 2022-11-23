package com.example.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavourRateItem(
    @PrimaryKey (autoGenerate = false)
    @ColumnInfo (name = "name")
    val name: String,

    @ColumnInfo (name = "rate")
    val rate: Double

)