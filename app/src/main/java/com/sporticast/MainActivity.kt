package com.sporticast

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sporticast.dto.request.BookRequest
import com.sporticast.model.Book
import com.sporticast.screens.FavoritesScreen
import com.sporticast.screens.home.HomeScreen
import com.sporticast.screens.LoginScreen
import com.sporticast.screens.ProfileScreen
import com.sporticast.screens.RegisterScreen
import com.sporticast.screens.WelcomeScreen
import com.sporticast.screens.admin.AddOrEditBookScreen
import com.sporticast.screens.admin.AdminDrawerScreen
import com.sporticast.screens.home.AudiobookDetailScreen
import com.sporticast.screens.home.PlayerScreen
import com.sporticast.viewmodel.AuthViewModel

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
    val authViewModel: AuthViewModel = viewModel()
    val isAdmin = authViewModel.isAdmin()
    val isLoggedIn = authViewModel.isLoggedIn()

    val startDest = when {
        !isLoggedIn -> "loginScreen"
        isAdmin -> "adminScreen"
        else -> "homeScreen"
    }
    val navController = rememberNavController()
    NavHost(
        navController,
        startDestination = startDest
    ) {

        composable("profile") {
            ProfileScreen(navController)
        }

        composable("favorites") {
            FavoritesScreen(navController)
        }

        composable("loginScreen") {
            LoginScreen(navController)
        }
        composable("registerScreen") {
            RegisterScreen(navController)
        }
        composable("homeScreen") {
            HomeScreen(navController)
        }
        composable("adminScreen") {
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

            PlayerScreen(
                title = title,
                author = author,
                duration = duration,
                audioUrl = audioUrl,
                navController = navController
            )
        }
        composable("addOrEditBook") {
            AddOrEditBookScreen(
                navController = navController,
                onSave = { bookRequest ->
                // Sau khi thêm sách thành công
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set("bookAddResult", "success")
                navController.popBackStack()
            })
        }

        composable(
            "addOrEditBook/{bookJson}",
            arguments = listOf(navArgument("bookJson") { type = NavType.StringType })
        ) { backStackEntry ->
            val bookJson = backStackEntry.arguments?.getString("bookJson") ?: ""
            val book = Json.decodeFromString<Book>(
                URLDecoder.decode(bookJson, StandardCharsets.UTF_8.toString())
            )

            AddOrEditBookScreen(
                navController = navController,
                book = book,
                onSave = { bookRequest ->
                    // TODO: Gọi ViewModel lưu sách tại đây

                    // Gửi kết quả về màn trước
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("bookAddResult", "success")

                    // Quay lại màn trước
                    navController.popBackStack()
        })
        }

        composable(
            route = "audiobookDetail/{bookJson}",
            arguments = listOf(navArgument("bookJson") { type = NavType.StringType })
        )
        { backStackEntry ->
            val bookJson = backStackEntry.arguments?.getString("bookJson") ?: ""
            val bookState = produceState<Book?>(initialValue = null, bookJson) {
                value = Json.decodeFromString<Book>(bookJson)
            }
            bookState.value?.let { book ->
                AudiobookDetailScreen(book = book, navController = navController)
            }

        }


    }
}


