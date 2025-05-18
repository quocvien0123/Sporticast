package com.sporticast.dto.request

data class AddAudiobookRequest(
    val playlistId: Int?,
    val playlistName: String?,
    val audiobookId: Int
)
