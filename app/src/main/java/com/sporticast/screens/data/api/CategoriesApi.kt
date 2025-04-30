package com.sporticast.screens.data.api
import com.sporticast.model.Category
import retrofit2.http.GET

interface CategoriesApi {
    @GET("users/categories")
    suspend fun getCategories(): List<Category>
}