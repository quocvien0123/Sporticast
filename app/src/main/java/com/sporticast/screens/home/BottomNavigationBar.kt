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
            val selected = currentDestination == item.route
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                        tint = if (selected) Color.White else Color.Gray
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        color = if (selected) Color.White else Color.Gray
                    )
                },
                selected = selected,
                onClick = {
                    navController.navigate(item.route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent // Không có nền làm nổi
                )
            )
        }
    }
}
