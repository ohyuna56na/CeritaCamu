package com.uasmobile.ceritakamu.ui.dashboard

import android.app.Application
import android.util.Log
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

    var currentUserId: Int = 0

    init {
        loadFavoriteBooks()
    }

    private fun loadFavoriteBooks() {
        viewModelScope.launch {
            val favorites = favoriteBookDao.getFavoritesByUser(currentUserId)
            Log.d("SharedViewModel", "Favorites loaded: $favorites")
            _favoriteBooks.postValue(favorites)
        }
    }

    suspend fun addFavoriteBook(book: BookItem) {
        val favoriteBook = FavoriteBook(
            id = book.id,
            title = book.volumeInfo.title,
            authors = book.volumeInfo.authors?.joinToString(", "),
            publisher = book.volumeInfo.publisher,
            publishedDate = book.volumeInfo.publishedDate,
            description = book.volumeInfo.description,
            readingModesText = book.volumeInfo.readingModes?.text,
            readingModesImage = book.volumeInfo.readingModes?.image,
            pageCount = book.volumeInfo.pageCount,
            printType = book.volumeInfo.printType,
            categories = book.volumeInfo.categories?.joinToString(", "),
            maturityRating = book.volumeInfo.maturityRating,
            allowAnonLogging = book.volumeInfo.allowAnonLogging,
            contentVersion = book.volumeInfo.contentVersion,
            containsEpubBubbles = book.volumeInfo.panelizationSummary?.containsEpubBubbles,
            containsImageBubbles = book.volumeInfo.panelizationSummary?.containsImageBubbles,
            thumbnailUrl = book.volumeInfo.imageLinks?.thumbnail,
            language = book.volumeInfo.language,
            previewLink = book.volumeInfo.previewLink,
            infoLink = book.volumeInfo.infoLink,
            canonicalVolumeLink = book.volumeInfo.canonicalVolumeLink,
            userId = currentUserId
        )
        favoriteBookDao.addFavorite(favoriteBook)
        loadFavoriteBooks()
    }

    suspend fun removeFavoriteBook(book: BookItem) {
        val favoriteBook = FavoriteBook(
            id = book.id,
            title = book.volumeInfo.title,
            authors = book.volumeInfo.authors?.joinToString(", "),
            publisher = book.volumeInfo.publisher,
            publishedDate = book.volumeInfo.publishedDate,
            description = book.volumeInfo.description,
            readingModesText = book.volumeInfo.readingModes?.text,
            readingModesImage = book.volumeInfo.readingModes?.image,
            pageCount = book.volumeInfo.pageCount,
            printType = book.volumeInfo.printType,
            categories = book.volumeInfo.categories?.joinToString(", "),
            maturityRating = book.volumeInfo.maturityRating,
            allowAnonLogging = book.volumeInfo.allowAnonLogging,
            contentVersion = book.volumeInfo.contentVersion,
            containsEpubBubbles = book.volumeInfo.panelizationSummary?.containsEpubBubbles,
            containsImageBubbles = book.volumeInfo.panelizationSummary?.containsImageBubbles,
            thumbnailUrl = book.volumeInfo.imageLinks?.thumbnail,
            language = book.volumeInfo.language,
            previewLink = book.volumeInfo.previewLink,
            infoLink = book.volumeInfo.infoLink,
            canonicalVolumeLink = book.volumeInfo.canonicalVolumeLink,
            userId = currentUserId
        )
        favoriteBookDao.removeFavorite(favoriteBook)
        loadFavoriteBooks()
    }

    suspend fun isFavorite(book: BookItem): Boolean {
        return favoriteBookDao.isFavorite(book.id, currentUserId) > 0
    }
}
