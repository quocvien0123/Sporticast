package com.sporticast.model

data class Review(
    var id: Int? = null,
    var userId: Int? = null,
    var audiobookId: Int? = null,
    var rating: Int? = null,
    var comment: String? = null,
    var createdAt: String? = null
)
