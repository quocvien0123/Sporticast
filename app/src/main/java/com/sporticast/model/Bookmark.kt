package com.sporticast.model

data class Bookmark(
    var id: Int? = null,
    var userId: Int? = null,
    var audiobookId: Int? = null,
    var chapterId: Int? = null,
    var position: Int? = null,
    var createdAt: String? = null
)
