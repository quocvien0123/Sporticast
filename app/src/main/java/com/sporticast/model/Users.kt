package com.sporticast.model

data class Users(
var id: Int? = null,
var name: String? = null,
var email: String? = null,
var password: String? = null,
var avatar: String? = null,
var role: String? = "user",
var createdAt: String? = null,
var updatedAt: String? = null
)
