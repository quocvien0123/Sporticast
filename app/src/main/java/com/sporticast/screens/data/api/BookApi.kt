package com.sporticast.screens.data.api

import com.sporticast.model.Book
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface BookApi {
    @GET("users/featuredbooks")
    suspend fun getBooks(): List<Book>

    @DELETE("admin/delete_book/{id}")
    suspend fun deleteBook(@Path("id") id: Long): Response<Void>
}