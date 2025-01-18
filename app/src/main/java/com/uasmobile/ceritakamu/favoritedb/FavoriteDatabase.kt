package com.uasmobile.ceritakamu.favoritedb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteBook::class], version = 1)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract fun favoriteBookDao(): FavoriteBookDao

    companion object {
        @Volatile
        private var INSTANCE: FavoriteDatabase? = null

        fun getDatabase(context: Context): FavoriteDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteDatabase::class.java,
                    "favorite_book_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
