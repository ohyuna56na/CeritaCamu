package com.uasmobile.ceritakamu.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uasmobile.ceritakamu.model.BookResponse
import com.uasmobile.ceritakamu.network.Retrofit
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val _searchQuery = MutableLiveData<String>()
    val searchQuery: LiveData<String> get() = _searchQuery

    private val _selectedLanguage = MutableLiveData<String>()
    val selectedLanguage: LiveData<String> get() = _selectedLanguage

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> get() = _message

    private val _bookResults = MutableLiveData<BookResponse>()
    val bookResults: LiveData<BookResponse> get() = _bookResults

    init {
        // Set default language to English
        _selectedLanguage.value = "en"
    }

    fun updateSearchQuery(query: String) {
        Log.d("SearchViewModel", "updateSearchQuery: Query updated to $query")
        _searchQuery.value = query
    }

    fun updateSelectedLanguage(language: String) {
        Log.d("SearchViewModel", "updateSelectedLanguage: Language updated to $language")
        _selectedLanguage.value = language
    }

    fun updateMessage(msg: String) {
        Log.d("SearchViewModel", "updateMessage: Message updated to $msg")
        _message.value = msg
    }

    fun searchBooks() {
        val query = _searchQuery.value?.trim() ?: ""
        val language = _selectedLanguage.value ?: "en" // Default to English

        Log.d("SearchViewModel", "searchBooks: Query = $query, Language = $language")

        if (query.isEmpty()) {
            val errorMessage = "Masukkan kata kunci untuk pencarian."
            Log.d("SearchViewModel", "searchBooks: $errorMessage")
            _message.value = errorMessage
            return
        }

        viewModelScope.launch {
            try {
                Log.d("SearchViewModel", "searchBooks: Making API call with query = $query and language = $language")
                val response = Retrofit.api.searchBooks(query, language)

                if (response.items.isNullOrEmpty()) {
                    val noResultsMessage = "Tidak ada hasil ditemukan."
                    Log.d("SearchViewModel", "searchBooks: $noResultsMessage")
                    _message.value = noResultsMessage
                } else {
                    _bookResults.value = response
                    val resultsMessage = "Ditemukan ${response.items.size} hasil."
                    Log.d("SearchViewModel", "searchBooks: $resultsMessage")
                    _message.value = resultsMessage
                }

            } catch (e: Exception) {
                val errorMessage = "Terjadi kesalahan: ${e.localizedMessage ?: e.message}".also {
                    Log.e("SearchViewModel", "searchBooks: Error occurred", e)
                }
                _message.value = errorMessage
            }
        }
    }
}