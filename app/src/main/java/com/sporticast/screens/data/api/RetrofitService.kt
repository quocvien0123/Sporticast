package com.sporticast.screens.data.api

import com.sporticast.viewmodel.AuthViewModel
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    private val gson = com.google.gson.GsonBuilder()
        .setLenient()
        .create()
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor { AuthViewModel().getToken() }) // Truyền hàm lấy token từ ViewModel
        .build()
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8080")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val registerApi: RegisterApi = retrofit.create(RegisterApi::class.java)
    val loginApi: LoginApi = retrofit.create(LoginApi::class.java)
    val categoryApi: CategoriesApi = retrofit.create(CategoriesApi::class.java)
    val bookApi: BookApi = retrofit.create(BookApi::class.java)
    val searchApi: SearchApi = retrofit.create(SearchApi::class.java)
    val adminManagerApi: AdminManagerApi = retrofit.create(AdminManagerApi::class.java)
    val userApi: UserApi = retrofit.create(UserApi::class.java)
    val playerApi: PlayerApi = retrofit.create(PlayerApi::class.java)


}