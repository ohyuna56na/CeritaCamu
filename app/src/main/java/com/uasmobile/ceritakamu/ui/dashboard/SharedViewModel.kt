package com.uasmobile.ceritakamu.ui.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.uasmobile.ceritakamu.favoritedb.FavoriteBook
import com.uasmobile.ceritakamu.favoritedb.FavoriteBookDao
import com.uasmobile.ceritakamu.favoritedb.FavoriteDatabase
import com.uasmobile.ceritakamu.model.BookItem
import kotlinx.coroutines.launch

class SharedViewModel(application: Application) : AndroidViewModel(application) {
    private val favoriteBookDao: FavoriteBookDao = FavoriteDatabase.getDatabase(application).favoriteBookDao()
    private val _favoriteBooks = MutableLiveData<List<FavoriteBook>>()
    val favoriteBooks: LiveData<List<FavoriteBook>> get() = _favoriteBooks

    init {
        loadFavoriteBooks()
    }

    private fun loadFavoriteBooks() {
        viewModelScope.launch {
            _favoriteBooks.postValue(favoriteBookDao.getAllFavorites())
        }
    }

    suspend fun addFavoriteBook(book: BookItem) {
        val favoriteBook = FavoriteBook(
            book.id,
            book.volumeInfo.title,
            book.volumeInfo.authors?.joinToString(", "),
            book.volumeInfo.imageLinks?.thumbnail
        )
        favoriteBookDao.addFavorite(favoriteBook)
        loadFavoriteBooks()
    }

    suspend fun removeFavoriteBook(book: BookItem) {
        val favoriteBook = FavoriteBook(
            book.id,
            book.volumeInfo.title,
            book.volumeInfo.authors?.joinToString(", "),
            book.volumeInfo.imageLinks?.thumbnail
        )
        favoriteBookDao.removeFavorite(favoriteBook)
        loadFavoriteBooks()
    }

    suspend fun isFavorite(book: BookItem): Boolean {
        return favoriteBookDao.isFavorite(book.id) > 0
    }
}
