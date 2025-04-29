package com.sporticast.screens.data.api

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import com.sporticast.dto.request.RegisterRequest

public interface RegisterApi {
    @POST("/api/auth/register")
    suspend fun registerUser(@Body request: RegisterRequest): Response<ResponseBody>
}