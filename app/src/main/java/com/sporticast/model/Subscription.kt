package com.sporticast.model
data class Subscription(
    var id: Int? = null,
    var userId: Int? = null,
    var plan: String? = "free", // "free", "premium", "vip"
    var startDate: String? = null,
    var endDate: String? = null
)
