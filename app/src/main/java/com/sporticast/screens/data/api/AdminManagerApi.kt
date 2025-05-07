package com.sporticast.screens.data.api

import com.sporticast.dto.request.User
import com.sporticast.model.Book
import retrofit2.http.GET

interface AdminManagerApi {
    @GET ("/admin/users")
    suspend fun getAllUsers(): List<User>
    @GET("/admin/audiobook")
    suspend fun getAllAudiobooks(): List<Book>
}