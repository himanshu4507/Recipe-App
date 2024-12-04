package com.example.recipeapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourites")
data class FavouritesEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title:String,
    val image: String,
    val cookingTime: Int
)
