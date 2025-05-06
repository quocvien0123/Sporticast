package com.sporticast.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sporticast.ui.theme.colorLg_Rg
import kotlinx.coroutines.launch
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

data class MenuItemData(val title: String, val icon: ImageVector)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDrawerScreen(navController: NavController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedItem by remember { mutableStateOf("Người dùng") }

    val menuItems = listOf(
        MenuItemData("Người dùng", Icons.Default.Person),
        MenuItemData("Vai trò", Icons.Default.Security),
        MenuItemData("Cài đặt", Icons.Default.Settings),
        MenuItemData("Đăng xuất", Icons.Default.ExitToApp)
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(300.dp),
                drawerContainerColor = Color.Transparent
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Brush.verticalGradient(colorLg_Rg))
                        .padding(16.dp)
                ) {
                    Spacer(Modifier.height(24.dp))
                    Text(
                        text = "🔧 Quản trị viên",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )
                    Spacer(Modifier.height(12.dp))

                    menuItems.forEach { item ->
                        NavigationDrawerItem(
                            label = { Text(item.title, color = Color.White) },
                            icon = {
                                Icon(item.icon, contentDescription = item.title, tint = Color.White)
                            },
                            selected = item.title == selectedItem,
                            onClick = {
                                selectedItem = item.title
                                scope.launch { drawerState.close() }
                                if (item.title == "Đăng xuất") {
                                    navController.navigate("homeScreen")
                                }
                            },
                            colors = NavigationDrawerItemDefaults.colors(
                                selectedContainerColor = Color(0xFF264B5D),
                                unselectedContainerColor = Color.Transparent,
                                selectedIconColor = Color.White,
                                unselectedIconColor = Color.LightGray
                            ),
                            modifier = Modifier
                                .padding(NavigationDrawerItemDefaults.ItemPadding)
                                .fillMaxWidth()
                        )
                    }

                }
            }
        }
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = { Text(selectedItem, color = Color.White) },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.White)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(Brush.verticalGradient(colorLg_Rg))
                    .padding(16.dp)
            ) {
                when (selectedItem) {
                    "Người dùng" -> UsersScreen()
                    "Vai trò" -> Text("📋 Danh sách vai trò", color = Color.White)
                    "Cài đặt" -> Text("⚙️ Cài đặt hệ thống", color = Color.White)
                    "Đăng xuất" -> Text("🚪 Đăng xuất...", color = Color.White)
                }
            }
        }
    }
}