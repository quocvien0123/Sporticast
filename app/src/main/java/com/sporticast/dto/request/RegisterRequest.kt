package com.sporticast.dto.request

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String
)