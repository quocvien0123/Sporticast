package com.sporticast.screens.data.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import com.sporticast.dto.request.LoginRequest
import com.sporticast.dto.response.LoginResponse
import com.sporticast.dto.response.VerifyResponse
import retrofit2.http.Query

public interface LoginApi   {
    @POST("/api/auth/login")
    suspend fun loginUser(@Body request: LoginRequest): Response<LoginResponse>
    @POST("/api/auth/verify-code")
    suspend fun verifyCode(
        @Query("email") email: String,
        @Query("code") code: String
    ): Response<VerifyResponse>

}