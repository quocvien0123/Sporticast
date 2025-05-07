package com.sporticast.dto.response

data class LoginResponse(
    val is_admin: Boolean,
    val message: String,
    val token: String
)