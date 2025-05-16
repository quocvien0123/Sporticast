package com.sporticast.model

import kotlinx.serialization.Serializable

@Serializable
data class Chapter(
    val id: Int,
    val title: String,
    val audiobookId: Int,
    val audioUrl: String,
    val duration: String,
    val createdAt: String
)
