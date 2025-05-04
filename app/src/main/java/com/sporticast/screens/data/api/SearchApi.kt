package com.sporticast.screens.data.api

import com.sporticast.model.Book
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {
    @GET("api/books/search")
    suspend fun searchAudiobooks(
        @Query("query") query: String
    ): List<Book>
}
