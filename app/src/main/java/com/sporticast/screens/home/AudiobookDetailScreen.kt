package com.sporticast.screens.home

import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sporticast.model.Book
import com.sporticast.ui.theme.colorLg_Rg
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import java.net.URLEncoder

fun decodeText(text: String): String {
    return URLDecoder.decode(text, StandardCharsets.UTF_8.toString())
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AudiobookDetailScreen(book: Book, navController: NavController) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Giới thiệu", "Mục lục", "Tương tự")

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colorLg_Rg)),
        topBar = {
            TopAppBar(
                title = { Text(text = "", color = Color.White, fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        containerColor = Color.Transparent,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(1.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = book.imageUrl,
                    contentDescription = "Ảnh bìa sách",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(12.dp))
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(
                        text = decodeText(book.title),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif,
                        color = Color.White,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = decodeText(book.author),
                        fontSize = 14.sp,
                        color = Color.Gray,
                        fontFamily = FontFamily.SansSerif
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = Color(0xFFFFC107),
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "${book.rating}",
                            fontSize = 14.sp,
                            color = Color.White,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }

                // Nút "Đánh dấu" (giống như ảnh)
                Button(
                    onClick = { /* TODO */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4B4B4B)),
                    shape = RoundedCornerShape(20.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text(text = "+ Đánh dấu", fontSize = 12.sp, color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            val blueGradient = Brush.horizontalGradient(
                colors = listOf(Color(0xFF42A5F5), Color(0xFF1E88E5))
            )

            Surface(
                onClick = {
                    val encodedAudioUrl = URLEncoder.encode(book.audioUrl, "UTF-8")
                    val encodedTitle = URLEncoder.encode(book.title, "UTF-8")
                    val encodedAuthor = URLEncoder.encode(book.author, "UTF-8")

                    navController.navigate("player/$encodedTitle/$encodedAuthor/${book.duration}/$encodedAudioUrl")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(56.dp)
                    .background(blueGradient, RoundedCornerShape(28.dp)),
                shape = RoundedCornerShape(28.dp),
                color = Color.Transparent,
                shadowElevation = 8.dp


            )

            {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Phát tất cả",
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Phát tất cả",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(40.dp))

            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = Color(0x66000000),
                contentColor = Color.White,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                        color = Color.White
                    )
                }
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = {
                            Text(
                                text = title,
                                color = if (selectedTabIndex == index) Color.White else Color.LightGray
                            )
                        }
                    )
                }
            }

            when (selectedTabIndex) {
                0 -> TabIntroContent(book)
                1 -> TabChaptersContent()
                2 -> TabSimilarBooksContent()
            }

            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}




@Composable
fun TabIntroContent(book: Book) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = decodeText(book.description ?: "Mô tả không có sẵn"),
            color = Color.White,
            fontSize = 15.sp,
            fontFamily = FontFamily.SansSerif,
            maxLines = 4,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(16.dp))

        InfoRow(label = "Tác giả", value = decodeText(book.author ?: "Tác giả không rõ"))
        InfoRow(label = "Thời lượng", value = book.duration)
        InfoRow(label = "Đánh giá", value = "⭐ ${book.rating}")
        InfoRow(label = "Nghe", value = "${book.listenCount} lần")
        InfoRow(label = "Ngôn ngữ", value = book.language)
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = Color.Gray,
            fontSize = 15.sp,
            fontFamily = FontFamily.SansSerif
        )
        Text(
            text = value,
            color = Color.White,
            fontSize = 15.sp,
            fontFamily = FontFamily.SansSerif,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
fun TabChaptersContent() {
    Column(modifier = Modifier.padding(16.dp)) {
        repeat(6) { index ->
            Text(
                text = "Chương ${index + 1}: Tên chương...",
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }
}

@Composable
fun TabSimilarBooksContent() {
    Text(
        text = "Bạn có thể thích những sách như: Atomic Habits, 7 Thói Quen Hiệu Quả...",
        color = Color.White,
        fontSize = 20.sp,
        modifier = Modifier.padding(16.dp)
    )
}
