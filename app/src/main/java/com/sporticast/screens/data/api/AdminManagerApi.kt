package com.sporticast.screens.data.api

import com.sporticast.dto.request.BookRequest
import com.sporticast.dto.request.User
import com.sporticast.model.Book
import retrofit2.Response // Correct import
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AdminManagerApi {
    @GET("/admin/users")
    suspend fun getAllUsers(): List<User>

    @GET("/admin/audiobook")
    suspend fun getAllAudiobooks(): List<Book>

    @POST("/admin/add_audiobook")
    suspend fun addAudiobook(@Body bookRequest: BookRequest): Response<Unit>

    @PUT("admin/edit_audiobook/{id}")
    suspend fun updateBook(
        @Path("id") id: Long,
        @Body dto: BookRequest
    ): Response<Book>



}
