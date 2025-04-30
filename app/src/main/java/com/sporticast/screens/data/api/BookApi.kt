package com.sporticast.screens.data.api

import com.sporticast.model.Book
import retrofit2.http.GET

interface BookApi {
    @GET("users/featuredbooks")
    suspend fun getBooks(): List<Book>
}