package com.sporticast.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.sporticast.Helper.TextToSpeechHelper
import com.sporticast.ui.theme.colorLg_Rg
import com.sporticast.viewmodel.AuthViewModel
import com.sporticast.viewmodel.BookViewModel
import com.sporticast.viewmodel.HomeViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    authViewModel:  AuthViewModel = viewModel(),
    bookViewModel: BookViewModel = viewModel(),
) {
    val imageUrls = listOf(
        // Atomic Habits - James Clear
        "https://cdn1.fahasa.com/media/catalog/product/8/9/8935270703691_1.jpg",

        // The Power of Now - Eckhart Tolle
        "https://m.media-amazon.com/images/I/71zytzrg6lL._AC_UF1000,1000_QL80_.jpg",

        // Can’t Hurt Me - David Goggins

        // The Subtle Art of Not Giving a F*ck - Mark Manson
        "https://m.media-amazon.com/images/I/71QKQ9mwV7L._AC_UF1000,1000_QL80_.jpg",

        // Think and Grow Rich - Napoleon Hill

        // Rich Dad Poor Dad - Robert T. Kiyosaki
        "https://m.media-amazon.com/images/I/81bsw6fnUiL._AC_UF1000,1000_QL80_.jpg",

    )

    val userId = authViewModel.getUserId()
    val context = LocalContext.current
    LaunchedEffect(userId) {
        TextToSpeechHelper.speakWithFPT(context, "Xin chào, chào mừng bạn đến với ứng dụng Spoticast")
        if (userId != null) {
            bookViewModel.loadFavorites(userId)
        }
    }

    val bookViewModel: BookViewModel = viewModel()
    val viewModel: HomeViewModel = viewModel()
    val categories by viewModel.categories.collectAsState()
    val featuredBooks by viewModel.featuredBooks.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val filteredBooks = if (searchQuery.isNotBlank()) {
        featuredBooks.filter { it.title.contains(searchQuery, ignoreCase = true) }
    } else {
        selectedCategory?.let { category ->
            featuredBooks.filter { it.category == category.name }
        } ?: featuredBooks
    }


    val gradientBrush = Brush.verticalGradient(colors = colorLg_Rg)

    var showProfileMenu by remember { mutableStateOf(false) }
    var showNotificationMenu by remember { mutableStateOf(false) }
    var showSettingsMenu by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()



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
                        .align(Alignment.BottomCenter)
                )
            }
        ) { paddingValues ->
        Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                HomeTopBar(
                    showProfileMenu = showProfileMenu,
                    showNotificationMenu = showNotificationMenu,
                    showSettingsMenu = showSettingsMenu,
                    onProfileMenuChange = { showProfileMenu = it },
                    onNotificationMenuChange = { showNotificationMenu = it },
                    onSettingsMenuChange = { showSettingsMenu = it },
                    onLogout = {
                        authViewModel.logout {
                            navController.navigate("loginScreen") {
                                popUpTo(0) {
                                    inclusive = true
                                }
                            }
                        }
                    }
                )

                SearchBar(
                    query = searchQuery,
                    onQueryChanged = { viewModel.setSearchQuery(it) },
                    onSearchTriggered = { viewModel.performSearch() }
                )

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    item {

                        CategorySection(
                            categories = categories,
                            selectedCategory = selectedCategory,
                            onClick = { viewModel.onCategorySelected(it) }
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        ImageCarouselFromUrls(imageUrls)
                        val categoryName = selectedCategory?.let { "${it.icon} ${it.name}" }
                        Text(
                            text = categoryName?.let { "Books in $it" } ?: "Featured Books",
                            fontWeight = FontWeight.Medium,
                            color = Color.White,

                            fontSize = 20.sp,
                            maxLines = 1
                        )
                        Spacer(modifier = Modifier.height(40.dp))


                    }

                    items(filteredBooks) { book ->
                        FeaturedContentItem(
                            book = book,
                            userId = userId,
                            bookViewModel = bookViewModel,
                            onClick = {
                                val bookJson = URLEncoder.encode(
                                    Json.encodeToString(book),
                                    StandardCharsets.UTF_8.toString()
                                )
                                navController.navigate("audiobookDetail/$bookJson")
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
                            isFavourite = bookViewModel.isFavorite(book.id)
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                    }


                }
            }
        }
    }
}
