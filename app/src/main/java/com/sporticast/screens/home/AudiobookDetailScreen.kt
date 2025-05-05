package com.sporticast.screens.home

import android.R.attr.onClick
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
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
import coil.compose.AsyncImage

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
            Spacer(modifier = Modifier.height(16.dp))

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
                        .size(120.dp)
                        .clip(RoundedCornerShape(12.dp))
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = book.title,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Tác giả ${book.author}", color = Color.LightGray, fontSize = 16.sp)
                    Text(
                        text = "Thể loại: ${book.category}",
                        color = Color.LightGray,
                        fontSize = 16.sp
                    )
                    Text(
                        text = "Thời lượng: ${book.duration}",
                        color = Color.LightGray,
                        fontSize = 16.sp
                    )
                    Text(
                        text = "Đánh giá: ⭐ ${book.rating}",
                        color = Color.LightGray,
                        fontSize = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            val blueGradient = Brush.horizontalGradient(
                colors = listOf(Color(0xFF42A5F5), Color(0xFF1E88E5))
            )

            Surface(
                onClick = {
                    navController.navigate("player/${book.title}/${book.author}/${book.duration}")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(56.dp)
                    .background(blueGradient, RoundedCornerShape(28.dp)),
                shape = RoundedCornerShape(28.dp),
                color = Color.Transparent,
                shadowElevation = 8.dp
            ) {
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
                Button(
                    onClick = {
                        navController.navigate("player/${book.title}/${book.author}/${book.duration}")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1DB954)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = "Play All", tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Phát tất cả", color = Color.White)
                }

            Spacer(modifier = Modifier.height(16.dp))

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
    Text(
        text = "Giới thiệu sách: Đây là cuốn sách rất hay của tác giả ${book.author}, kể về hành trình khám phá bản thân, và được đánh giá rất cao bởi người đọc.",
        color = Color.White,
        fontSize = 15.sp,
        modifier = Modifier.padding(16.dp)
    )
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
