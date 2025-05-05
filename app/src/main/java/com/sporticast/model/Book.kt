package com.sporticast.model

import kotlinx.serialization.Serializable

@Serializable
data class Book(
    val id:String,
    val title: String,
    val author: String,
    val duration: String,
    val imageUrl: String,
    val rating: Float,
    val listenCount: Int,
    val category: String
)