package com.sporticast.screens.data.api

import com.sporticast.dto.request.AddAudiobookRequest
import com.sporticast.model.AudiobookDTO
import com.sporticast.model.Book
import com.sporticast.model.Playlist
import com.sporticast.model.PlaylistDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PlaylistApi {
    @POST("users/playlist")
    suspend fun createOrAdd(
        @Body req: AddAudiobookRequest
    ): Response<Playlist>

    @GET("users/playlist/{id}/audiobooks")
    suspend fun getAudiobooksInPlaylist(
        @Path("id") playlistId: Int
    ): Response<List<Book>>

        @GET("/users/playlists")
        suspend fun getAllPlaylists(): Response<List<PlaylistDTO>>
    }
