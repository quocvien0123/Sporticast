package com.sporticast.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Book(
    val id:String,
    val title: String,
    val author: String,
    val duration: String,
    val imageUrl: String,
    val rating: Float,
    val listenCount: Int,
    val category: String,
    val description: String,
    val language: String,
    val audioUrl: String,
): Parcelable