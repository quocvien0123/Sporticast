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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.sporticast.ui.theme.colorLg_Rg
import com.sporticast.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val viewModel: HomeViewModel = viewModel()
    val categories by viewModel.categories.collectAsState()
    val featuredBooks by viewModel.featuredBooks.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    var showProfileMenu by remember { mutableStateOf(false) }
    var showNotificationMenu by remember { mutableStateOf(false) }
    var showSettingsMenu by remember { mutableStateOf(false) }


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
                .background(Brush.verticalGradient(colors = colorLg_Rg))
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


            SearchBar(searchQuery) { viewModel.onSearchQueryChanged(it) }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                item {
                    CategorySection(
                        categories = categories,
                        onClick = { viewModel.onCategorySelected(it) }
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Featured Books",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }

                items(featuredBooks) { book ->
                    FeaturedContentItem(
                        book = book,
                        onClick = { viewModel.onBookSelected(book) }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}
