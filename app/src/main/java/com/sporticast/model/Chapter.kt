package com.sporticast.model

data class Chapter(
    var id: Int? = null,
    var audiobookId: Int? = null,
    var title: String? = null,
    var audioUrl: String? = null,
    var duration: Int? = null,
    var order: Int? = null,
    var createdAt: String? = null
)
