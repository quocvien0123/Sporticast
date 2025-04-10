package com.sporticast.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)
val gradient = listOf(
    Color(0xFF2A2A2A),
    Color(0xFF1A1A1A)
)

// Sử dụng gradient trong Compose:
// 1. Với Box/Surface/etc:
// .background(Brush.verticalGradient(colors = gradient))
//
// 2. Với Button:
// .background(Brush.verticalGradient(colors = gradient))
//
// 3. Với Card:
// .background(Brush.verticalGradient(colors = gradient))