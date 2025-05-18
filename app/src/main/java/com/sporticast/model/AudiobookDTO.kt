package com.sporticast.model

import kotlinx.serialization.Serializable

@Serializable
data class AudiobookDTO(
    val id: Int,
    val title: String,
    val author: String,
    val duration: Int,
    val rating: Float,
    val listenCount: Int
)