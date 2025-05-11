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
import com.sporticast.ui.theme.colorLg_Rg
import com.sporticast.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

data class MenuItemData(val title: String, val icon: ImageVector)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDrawerScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedItem by remember { mutableStateOf("Người dùng") }
    val snackbarHostState = remember { SnackbarHostState() }

    // Lắng nghe kết quả trả về từ BooksScreen
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
        MenuItemData("Người dùng", Icons.Default.Person),
        MenuItemData("Sách nói điện tử", Icons.Default.Book),
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
                                    authViewModel.logout {
                                        navController.navigate("loginScreen") {
                                            popUpTo(0) { inclusive = true }
                                        }
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
                SnackbarHost(
                    hostState = snackbarHostState,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
                when (selectedItem) {
                    "Người dùng" -> UsersScreen()
                    "Sách nói điện tử" -> BooksScreen(navController = navController)
                    "Cài đặt" -> Text("⚙️ Cài đặt hệ thống", color = Color.White)
                    "Đăng xuất" -> Text("🚪 Đăng xuất...", color = Color.White)
                }
            }
        }
    }
}
