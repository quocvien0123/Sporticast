package com.sporticast.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.sporticast.Helper.TextToSpeechHelper
import com.sporticast.screens.home.BottomNavigationBar
import com.sporticast.ui.theme.colorLg_Rg
import com.sporticast.screens.home.FeaturedContentItem
import com.sporticast.viewmodel.BookViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun FavoritesScreen(
    navController: NavController,
    userId: Long,
    bookViewModel: BookViewModel = viewModel()
) {
    // Đọc danh sách sách yêu thích từ ViewModel
    val favorites by bookViewModel.favoriteBooks

    // Load danh sách yêu thích khi có userId mới
  val context = LocalContext.current
    val hasSpoken = remember { mutableStateOf(false) }
    val isSpeaking = remember { mutableStateOf(false) }

    LaunchedEffect(userId) {
        bookViewModel.loadFavorites(userId)
        if (!hasSpoken.value) {
            hasSpoken.value = true
            TextToSpeechHelper.speakWithFPT(
                context,
                "Đây là danh sách sách bạn yêu thích",
                onComplete = { isSpeaking.value = false }
            )
        }
    }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val gradientBrush = Brush.verticalGradient(colors = colorLg_Rg)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBrush)
    ) {
        Scaffold(
            bottomBar = { BottomNavigationBar(navController) },
            containerColor = Color.Transparent,
            snackbarHost = {
                SnackbarHost(
                    hostState = snackbarHostState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .align(Alignment.TopCenter)
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Brush.verticalGradient(colors = colorLg_Rg))
                    .padding(innerPadding)
            ) {
                // Header
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Yêu thích",
                        color = Color.White,
                        fontSize = 20.sp,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = "More", tint = Color.White)
                    }
                }

                // Hiển thị nếu không có sách yêu thích nào
                if (favorites.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Không có sách yêu thích nào.", color = Color.White, fontSize = 16.sp)
                    }
                } else {
                    // Hiển thị danh sách yêu thích với LazyColumn
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(favorites) { book ->
                            FeaturedContentItem(
                                book = book,
                                userId = userId,
                                onClick = {
                                    // Điều hướng đến màn chi tiết sách
                                    val encoded = URLEncoder.encode(Json.encodeToString(book), StandardCharsets.UTF_8.toString())
                                    navController.navigate("audiobookDetail/$encoded")
                                },
                                onFavouriteClick = { uId, bookId ->
                                    if (uId != null) {
                                        bookViewModel.toggleFavourite(uId, bookId) { success, isNowFavorite ->
                                            coroutineScope.launch {
                                                val message = when {
                                                    success && isNowFavorite -> "✅ Đã thêm vào yêu thích"
                                                    success && !isNowFavorite -> "✅ Đã xóa khỏi yêu thích"
                                                    else -> "Thao tác thất bại"
                                                }
                                                snackbarHostState.showSnackbar(message)
                                            }
                                        }
                                    }
                                },
                                isFavourite = true,
                                bookViewModel = bookViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}
