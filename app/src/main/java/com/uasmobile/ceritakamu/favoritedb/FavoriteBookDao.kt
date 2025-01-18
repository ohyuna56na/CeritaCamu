package com.uasmobile.ceritakamu.favoritedb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteBookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(book: FavoriteBook)

    @Delete
    suspend fun removeFavorite(book: FavoriteBook)

    @Query("SELECT * FROM favorite_books")
    suspend fun getAllFavorites(): List<FavoriteBook>

    @Query("SELECT COUNT(*) FROM favorite_books WHERE id = :bookId")
    suspend fun isFavorite(bookId: String): Int
}