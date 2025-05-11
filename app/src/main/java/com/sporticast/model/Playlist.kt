package com.sporticast.model

data class Playlist(
    var id: String,
    var userId: Int? = null,
    var name: String,
    var createdAt: String
)
