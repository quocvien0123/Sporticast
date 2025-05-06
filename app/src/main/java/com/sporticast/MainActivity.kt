package com.sporticast

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sporticast.model.Book
import com.sporticast.screens.FavoritesScreen
import com.sporticast.screens.home.HomeScreen
import com.sporticast.screens.LoginScreen
import com.sporticast.screens.ProfileScreen
import com.sporticast.screens.RegisterScreen
import com.sporticast.screens.WelcomeScreen
import com.sporticast.screens.admin.AdminDrawerScreen
import com.sporticast.screens.home.AudiobookDetailScreen
import com.sporticast.screens.home.PlayerScreen

import com.sporticast.viewmodel.HomeViewModel
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import java.nio.charset.StandardCharsets


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigator()
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun AppNavigator() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "adminScreen") { // chinh lai wellcomeScreen sau khi hoan thanh
        composable("profile") {
            ProfileScreen(navController)
        }
        composable("home") {
            HomeScreen(navController)
        }
        composable("favorites") {
            FavoritesScreen(navController)
        }
        composable("admin") {
            AdminDrawerScreen(navController)
        }
        composable(
            route = "player/{title}/{author}/{duration}/{audioUrl}",
            arguments = listOf(
                navArgument("title") { type = NavType.StringType },
                navArgument("author") { type = NavType.StringType },
                navArgument("duration") { type = NavType.StringType },
                navArgument("audioUrl") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") ?: ""
            val author = backStackEntry.arguments?.getString("author") ?: ""
            val duration = backStackEntry.arguments?.getString("duration") ?: ""
            val encodedAudioUrl = backStackEntry.arguments?.getString("audioUrl") ?: ""
            val audioUrl = URLDecoder.decode(encodedAudioUrl, StandardCharsets.UTF_8.toString())

            PlayerScreen(title = title,
                author = author,
                duration = duration,
                audioUrl = audioUrl,
                navController = navController)
        }
        composable("audiobookDetail") {
            val book = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<Book>("book")

            if (book != null) {
                AudiobookDetailScreen(book = book, navController = navController)
            } else {
                // Hiển thị lỗi nếu không có dữ liệu
                Text("Không có dữ liệu sách", color = Color.White)
            }
        }



//        composable("login") { LoginScreen(navController) }
//        composable("register") { RegisterScreen(navController) }
        composable("homeScreen") { HomeScreen(navController) }
        composable("adminScreen") { AdminDrawerScreen(navController) }
//        composable("welcomeScreen") { WelcomeScreen(navController) }
    }
}


