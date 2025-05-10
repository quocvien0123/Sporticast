package com.sporticast.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class User(
        val id : Int,
        val name: String,
        val email: String,
        val isAdmin: Boolean,
        val avatar: String,
        val createdAt: String,
        val updatedAt: String

    )
