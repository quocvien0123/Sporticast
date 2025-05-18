package com.sporticast.screens.data.api

import com.sporticast.dto.request.BookRequest
import com.sporticast.dto.request.User
import com.sporticast.dto.request.admin.ChapterLimitRequest
import com.sporticast.dto.request.admin.ChapterRequest
import com.sporticast.model.Book
import retrofit2.Response // Correct import
import retrofit2.http.Body
import retrofit2.http.DELETE
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
    @DELETE("admin/delete_user/{id}")
    suspend fun deleteUser(@Path("id") id: Int): Response<Void>

    @PUT("admin/{id}/chapter-limit")
    suspend fun updateChapterLimit(
        @Path("id") audiobookId: Int,
        @Body request: ChapterLimitRequest
    ): Response<Unit>

    @POST("admin/{id}/chapters")
    suspend fun addChapter(
        @Path("id") audiobookId: Int,
        @Body chapter: ChapterRequest
    ): Response<Unit>
    @GET("admin/{id}/chapter-count")
    suspend fun getChapterCount(@Path("id") audiobookId: Int): Response<Int>

    @GET("admin/audiobooks/{id}")
    suspend fun getAudiobook(@Path("id") id: Int): Response<Book>  // Audiobook: data class bạn tự định nghĩa







}
