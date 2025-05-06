package com.sporticast.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.sporticast.ui.theme.colorLg_Rg
import com.sporticast.viewmodel.HomeViewModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
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

        // Tôi thấy hoa vàng trên cỏ xanh
        "https://cdn0.fahasa.com/media/catalog/product/8/9/8934974155274.jpg",

        // Cô gái đến từ hôm qua
        "https://cdn0.fahasa.com/media/catalog/product/8/9/8934974173445.jpg",

        // Cho tôi xin một vé đi tuổi thơ
        "https://cdn0.fahasa.com/media/catalog/product/8/9/8934974158015.jpg",

        // Ngồi khóc trên cây
        "https://cdn0.fahasa.com/media/catalog/product/8/9/8934974149105.jpg",

        // Bảy bước tới mùa hè
        "https://cdn0.fahasa.com/media/catalog/product/8/9/8934974138772.jpg"
    )

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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBrush)
    ){
        Scaffold(
            bottomBar = {
                BottomNavigationBar(navController)
            },
            containerColor = Color.Transparent
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
                        navController.navigate("login") {
                            popUpTo(navController.graph.startDestinationId) { inclusive = true }
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
                            onClick = {
                                // Khi người dùng bấm vào 1 cuốn sách nào đó
                                navController.currentBackStackEntry?.savedStateHandle?.set("book", book)
                                navController.navigate("audiobookDetail")

                            }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                }
            }
        }
    }
}
