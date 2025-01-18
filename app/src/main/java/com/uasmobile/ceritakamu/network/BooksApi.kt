package com.uasmobile.ceritakamu.network

import com.uasmobile.ceritakamu.model.BookResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BooksApi {
    @GET("volumes")
    suspend fun getBooks(
        @Query("q") query: String,
        @Query("key") apiKey: String,
        @Query("startIndex") startIndex: Int = 0,
        @Query("maxResults") maxResults: Int = 40
    ): BookResponse

    @GET("volumes")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("langRestrict") language: String,
        @Query("key") apiKey: String = Retrofit.API_KEY
    ): BookResponse
}
