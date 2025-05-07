package com.sporticast.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class User(
        val id : Int,
        val name: String,
        val email: String,
        val is_admin: Boolean,
        val createdAt: String,
        val updatedAt: String

    )
