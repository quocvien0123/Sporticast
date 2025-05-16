package com.sporticast.screens.data.api

import com.sporticast.model.Book
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface BookApi {
    @GET("users/featuredbooks")
    suspend fun getBooks(): List<Book>

    @DELETE("admin/delete_book/{id}")
    suspend fun deleteBook(@Path("id") id: Long): Response<Void>

    @POST("users/{userId}/favorites/{bookId}")
    suspend fun addToFavorites(
        @Path("userId") userId: Long?,
        @Path("bookId") bookId: Long
    ): Response<ResponseBody>

    @DELETE("users/{userId}/favorites/{bookId}")
    suspend fun removeFromFavorites(
        @Path("userId") userId: Long?,
        @Path("bookId") bookId: Long
    ): Response<ResponseBody>


    @GET("users/{userId}/favorites")
    suspend fun getFavorites(@Path("userId") userId: Long): Response<List<Book>>


}