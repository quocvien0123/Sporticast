package com.sporticast.model

import kotlinx.serialization.Serializable

@Serializable
data class Book(
    val id: Long,
    val title: String,
    val author: String,
    val duration: String,
    val imageUrl: String,
    val rating: Float,
    val listenCount: Int,
    val category: String,
    val description: String,
    val language: String,
    val audioUrl: String,
    val chapterLimit: Int,
)