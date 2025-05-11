package com.sporticast.screens.home

    import androidx.compose.foundation.background
    import androidx.compose.material.icons.Icons
    import androidx.compose.material.icons.filled.*
    import androidx.compose.material3.*
    import androidx.compose.runtime.Composable
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.graphics.Brush
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.graphics.vector.ImageVector
    import androidx.navigation.NavController
    import androidx.navigation.compose.currentBackStackEntryAsState
    import com.sporticast.ui.theme.colorLg_Rg

    data class BottomNavItem(val title: String, val icon: ImageVector, val route: String)

    @Composable
    fun BottomNavigationBar(navController: NavController) {
        val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route

        NavigationBar(
                containerColor = Color.DarkGray,
            modifier = Modifier.background(
                Brush.verticalGradient(colors = colorLg_Rg)
            )
        ) {
            val items = listOf(
                BottomNavItem("Home", Icons.Default.Home, "homeScreen"),
                BottomNavItem("Favorites", Icons.Default.Favorite, "favorites"),
                BottomNavItem("PlayList", Icons.Default.PlaylistAdd, "playlist"),
                BottomNavItem("Profile", Icons.Default.Person, "profile")
            )

            items.forEach { item ->
                NavigationBarItem(
                    icon = { Icon(item.icon, contentDescription = item.title) },
                    label = { Text(item.title) },
                    selected = currentDestination == item.route,
                    onClick = {
                        navController.navigate(item.route) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.Gray,
                        selectedTextColor = Color.White,
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray,
                        indicatorColor = if (currentDestination == item.route) Color.White else Color.Transparent
                    )
                )
            }
        }
    }