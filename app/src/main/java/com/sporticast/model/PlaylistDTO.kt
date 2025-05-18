package com.sporticast.model

data class PlaylistDTO(
    val id: Int,
    val name: String,
    val createdAt: String // hoặc Instant nếu bạn xử lý định dạng ISO
)