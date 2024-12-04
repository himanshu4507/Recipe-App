package com.example.recipeapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface FavouriteDao {

    @Insert
    suspend fun addToFavourites(favourites : FavouritesEntity)

    @Query("SELECT * FROM favourites")
    fun getAllFavourites(): LiveData<List<FavouritesEntity>>

    @Delete
    suspend fun deleteFromFavourites(favourites: FavouritesEntity)

    @Query("SELECT EXISTS (SELECT 1 FROM favourites WHERE id = :id)")
    suspend fun isFavorite(id: Int): Boolean




}