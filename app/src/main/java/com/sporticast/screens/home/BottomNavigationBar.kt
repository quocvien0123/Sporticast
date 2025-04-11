package com.sporticast.screens.home


import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sporticast.ui.theme.colorLg_Rg

data class BottomNavItem(val title: String, val icon: ImageVector, val route: String)

val bottomNavItems = listOf(
    BottomNavItem("Home", Icons.Default.Home, "home"),
    BottomNavItem("Explore", Icons.Default.Search, "explore"),
    BottomNavItem("Library", Icons.Default.LibraryBooks, "library"),
    BottomNavItem("Settings", Icons.Default.Settings, "settings")
)

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar(
        containerColor = Color.Transparent,
        modifier = Modifier.background(
            Brush.verticalGradient(colors = colorLg_Rg)
        )
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Trang chủ") },
            label = { Text("Home") },
            selected = true,
            onClick = { navController.navigate("home") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = Color.White,
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray,
                indicatorColor = Color.Transparent
            )
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Favorite, contentDescription = "Yêu thích") },
            label = { Text("Favorites") },
            selected = false,
            onClick = { navController.navigate("favorites") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = Color.White,
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray,
                indicatorColor = Color.Transparent
            )
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Hồ sơ") },
            label = { Text("Profile") },
            selected = false,
            onClick = { navController.navigate("profile") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = Color.White,
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray,
                indicatorColor = Color.Transparent
            )
        )
    }
}
