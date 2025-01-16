package com.uasmobile.ceritakamu.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.uasmobile.ceritakamu.model.BookItem
import com.uasmobile.ceritakamu.repository.BookRepository

class BookPagingSource(
    private val repository: BookRepository,
    private val query: String
) : PagingSource<Int, BookItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BookItem> {
        return try {
            val currentPage = params.key ?: 0
            val response = repository.fetchBooks(query, currentPage * 40, 40)
            val books = response.items ?: emptyList()

            LoadResult.Page(
                data = books,
                prevKey = if (currentPage == 0) null else currentPage - 1,
                nextKey = if (books.isEmpty()) null else currentPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, BookItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}