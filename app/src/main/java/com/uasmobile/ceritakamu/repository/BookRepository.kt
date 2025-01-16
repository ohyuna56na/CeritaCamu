package com.uasmobile.ceritakamu.repository

import com.uasmobile.ceritakamu.model.BookResponse
import com.uasmobile.ceritakamu.network.Retrofit


class BookRepository {
    suspend fun fetchBooks(query: String, startIndex: Int, maxResults: Int): BookResponse {
        return Retrofit.api.getBooks(
            query = query,
            apiKey = Retrofit.API_KEY,
            startIndex = startIndex,
            maxResults = maxResults
        )
    }
}