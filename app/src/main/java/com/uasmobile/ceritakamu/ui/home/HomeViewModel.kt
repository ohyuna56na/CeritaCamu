package com.uasmobile.ceritakamu.ui.home

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.uasmobile.ceritakamu.model.BookItem
import com.uasmobile.ceritakamu.paging.BookPagingSource
import com.uasmobile.ceritakamu.repository.BookRepository
import kotlinx.coroutines.flow.Flow

class HomeViewModel(private val repository: BookRepository) : ViewModel() {

    fun getBooks(query: String): Flow<PagingData<BookItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 40,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { BookPagingSource(repository, query) }
        ).flow
    }
}
