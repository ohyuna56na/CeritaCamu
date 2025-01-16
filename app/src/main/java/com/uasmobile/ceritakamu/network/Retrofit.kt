package com.uasmobile.ceritakamu.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofit {
    private const val BASE_URL = "https://www.googleapis.com/books/v1/"
    const val API_KEY = "AIzaSyD1omCLupYTMybjHABVU7HlUPQQHwsCcS4"

    val api: BooksApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BooksApi::class.java)
    }
}