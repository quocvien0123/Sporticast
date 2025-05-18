package com.sporticast.dto.request.admin

data class ChapterRequest(
    val title: String,
    val audioUrl: String,
    val duration: Int
)