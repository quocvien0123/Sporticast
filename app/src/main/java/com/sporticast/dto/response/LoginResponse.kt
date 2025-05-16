package com.sporticast.dto.response

data class LoginResponse(
    val status: String?,
    val is_admin: Boolean,
    val message: String,
    val token: String,
    val user_id: Long,
    val email: String
)