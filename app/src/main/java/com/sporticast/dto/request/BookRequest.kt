package com.sporticast.dto.request

data class BookRequest(
    val title: String,
    val author: String,
    val category: String,
    val audioUrl: String,
    val description: String,
    val duration: String,
    val imageUrl: String,
    val language: String,
    val listenCount: Int,
    val rating: Float,
)
