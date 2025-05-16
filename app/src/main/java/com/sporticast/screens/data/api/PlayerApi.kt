package com.sporticast.screens.data.api

import com.sporticast.model.Chapter
import retrofit2.http.GET
import retrofit2.http.Path

interface PlayerApi {
    @GET("/users/{audiobookId}/chapters")
    suspend fun getChapterByAudio(@Path("audiobookId") audiobookId: String): List<Chapter>
    @GET("/users/chapters/{chapterId}")
    suspend fun getChapterById(@Path("chapterId") chapterId: Int): Chapter
}