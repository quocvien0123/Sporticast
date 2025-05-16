package com.sporticast.screens.data.api

import com.sporticast.dto.response.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApi {
    @GET("users/user/{id}")
    suspend fun getUserById(@Path("id") id: Long): Response<UserResponse>
}
