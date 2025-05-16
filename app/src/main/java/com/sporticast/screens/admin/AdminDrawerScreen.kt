package com.sporticast.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sporticast.ui.theme.colorLg_Rg
import com.sporticast.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

data class MenuItemData(val title: String, val icon: ImageVector, val route: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDrawerScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val drawerNavController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }

    // Lắng nghe kết quả từ màn hình thêm/cập nhật sách
    val resultFlow = navController
        .currentBackStackEntry
        ?.savedStateHandle
        ?.getStateFlow("bookAddResult", "")

    val resultValue by resultFlow?.collectAsState() ?: remember { mutableStateOf("") }

    LaunchedEffect(resultValue) {
        when (resultValue) {
            "success" -> snackbarHostState.showSnackbar("📘 Thêm sách thành công!")
            "updated" -> snackbarHostState.showSnackbar("💾 Cập nhật sách thành công!")
        }
        if (resultValue.isNotBlank()) {
            navController.currentBackStackEntry?.savedStateHandle?.set("bookAddResult", "")
        }
    }

    val menuItems = listOf(
        MenuItemData("Người dùng", Icons.Default.Person, "usersScreen"),
        MenuItemData("Sách nói điện tử", Icons.Default.Book, "booksScreen"),
        MenuItemData("Cài đặt", Icons.Default.Settings, "settingsScreen"),
        MenuItemData("Đăng xuất", Icons.Default.ExitToApp, "logout")
    )

    var selectedRoute by remember { mutableStateOf("usersScreen") }

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
                            selected = selectedRoute == item.route,
                            onClick = {
                                scope.launch { drawerState.close() }
                                if (item.route == "logout") {
                                    authViewModel.logout {
                                        navController.navigate("loginScreen") {
                                            popUpTo(0) { inclusive = true }
                                        }
                                    }
                                } else {
                                    selectedRoute = item.route
                                    drawerNavController.navigate(item.route) {
                                        launchSingleTop = true
                                    }
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
                    title = {
                        val currentItem = menuItems.find { it.route == selectedRoute }
                        Text(currentItem?.title ?: "", color = Color.White)
                    },
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
            ) {
                SnackbarHost(
                    hostState = snackbarHostState,
                    modifier = Modifier.align(Alignment.TopCenter)
                )

                // 🧭 Điều hướng màn hình bên trong drawer
                AdminNavHost(
                    navController = drawerNavController,
                    parentNavController = navController // để truyền lại nếu cần
                )
            }
        }
    }
}

@Composable
fun AdminNavHost(
    navController: NavHostController,
    parentNavController: NavController
) {
    NavHost(
        navController = navController,
        startDestination = "usersScreen"
    ) {
        composable("usersScreen") {
            UsersScreen()
        }
        composable("booksScreen") {
            BooksScreen(navController = parentNavController)
        }
        composable("settingsScreen") {
            Text("⚙️ Cài đặt hệ thống", color = Color.White)
        }
    }
}
