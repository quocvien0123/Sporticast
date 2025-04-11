package com.sporticast.model

data class Audiobook(
    var id: Int? = null,
    var title: String? = null,
    var author: String? = null,
    var narrator: String? = null,
    var coverImage: String? = null,
    var description: String? = null,
    var duration: Int? = null,
    var categoryId: Int? = null,
    var createdAt: String? = null,
    var updatedAt: String? = null
)
