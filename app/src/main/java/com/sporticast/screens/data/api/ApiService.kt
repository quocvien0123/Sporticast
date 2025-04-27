
package com.sporticast.screens.data.api

import android.R.attr.name
import android.R.attr.password
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.POST

// Request body
data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String
)
data class LoginRequest(
    val email: String,
    val password: String
)
data class LoginResponse(

    val message: String

)


// Response body (nếu server trả về)
data class RegisterResponse(
    val message: String
)

interface ApiService {
    @POST("/api/auth/register")
    suspend fun registerUser(@Body request: RegisterRequest): Response<ResponseBody>
}
interface AuthApiService{
    @POST("/api/auth/login")
    suspend fun loginUser(@Body request: LoginRequest): Response<ResponseBody>
}

object RetrofitClient {
    private val gson = com.google.gson.GsonBuilder()
        .setLenient()
        .create()
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8080") // Nếu chạy trên emulator, localhost = 10.0.2.2
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
    val apiServiceLogin: AuthApiService = retrofit.create(AuthApiService::class.java)




}