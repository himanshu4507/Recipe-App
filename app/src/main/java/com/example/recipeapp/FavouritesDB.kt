package com.example.recipeapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavouritesEntity::class], version = 1)
abstract class FavouritesDB : RoomDatabase() {

    abstract fun favouritesDAO(): FavouriteDao

    companion object {
        @Volatile
        private var INSTANCE: FavouritesDB? = null

        fun getDatabase(context: Context): FavouritesDB {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FavouritesDB::class.java,
                        "FavouritesDB"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}
