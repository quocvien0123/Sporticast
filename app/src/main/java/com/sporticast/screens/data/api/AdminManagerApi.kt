package com.sporticast.screens.data.api

import retrofit2.Call
import com.sporticast.dto.request.BookRequest
import com.sporticast.dto.request.User
import com.sporticast.model.Book
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AdminManagerApi {
    @GET ("/admin/users")
    suspend fun getAllUsers(): List<User>
    @GET("/admin/audiobook")
    suspend fun getAllAudiobooks(): List<Book>
    @POST("/admin/add_audiobook")
    fun addAudioBook(@Body dto: BookRequest): Call<List<BookRequest>>
}