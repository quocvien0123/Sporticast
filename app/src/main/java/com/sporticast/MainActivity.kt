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
import com.sporticast.model.Book
import com.sporticast.screens.FavoritesScreen
import com.sporticast.screens.home.HomeScreen
import com.sporticast.screens.LoginScreen
import com.sporticast.screens.ProfileScreen
import com.sporticast.screens.RegisterScreen
import com.sporticast.screens.admin.AddOrEditBookScreen
import com.sporticast.screens.admin.AdminDrawerScreen
import com.sporticast.screens.auth.VerifyCodeScreen
import com.sporticast.screens.home.AudiobookDetailScreen
import com.sporticast.screens.home.PlayListScreen
import com.sporticast.screens.home.PlayerScreen
import com.sporticast.viewmodel.AuthViewModel
import com.sporticast.viewmodel.ProfileViewModel

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

@SuppressLint("StateFlowValueCalledInComposition", "ComposableDestinationInComposeScope")
@Composable
fun AppNavigator() {
    val authViewModel: AuthViewModel = viewModel()
    val isAdmin = authViewModel.isAdmin()
    val isLoggedIn = authViewModel.isLoggedIn()
    val profileViewModel: ProfileViewModel = viewModel()
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
            ProfileScreen(
                navController,
                viewModel = profileViewModel,
                userId = authViewModel.getUserId() ?: 0L
            )
        }

        composable("profile") { ProfileScreen(navController) }
        composable("favorites") {
            val userId = viewModel<AuthViewModel>().getUserId()
            if (userId != null) FavoritesScreen(navController, userId)
        }
        composable("playlist") { PlayListScreen(navController) }
        composable("loginScreen") { LoginScreen(navController) }
        composable("registerScreen") { RegisterScreen(navController) }
        composable("homeScreen") { HomeScreen(navController) }
        composable("adminScreen") { AdminDrawerScreen(navController) }

        composable("playlist") {
            PlayListScreen(navController)
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

        composable("verifyCodeScreen/{email}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            VerifyCodeScreen(
                navController = navController,
                email = email
            )
        }




        // --- Player screen
        composable(
            route = "player/{title}/{author}/{duration}/{audioUrl}/{audiobookId}",
            arguments = listOf(
                navArgument("title") { type = NavType.StringType },
                navArgument("author") { type = NavType.StringType },
                navArgument("duration") { type = NavType.StringType },
                navArgument("audioUrl") { type = NavType.StringType },
                navArgument("audiobookId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") ?: ""
            val author = backStackEntry.arguments?.getString("author") ?: ""
            val duration = backStackEntry.arguments?.getString("duration") ?: ""
            val audioUrl = URLDecoder.decode(
                backStackEntry.arguments?.getString("audioUrl") ?: "",
                StandardCharsets.UTF_8.toString()
            )
            val audiobookId = backStackEntry.arguments?.getInt("audiobookId") ?: 0

            PlayerScreen(
                title = title,
                author = author,
                duration = duration,
                audioUrl = audioUrl,
                navController = navController,
                audiobookId = audiobookId,
                chapterViewModel = viewModel()
            )
        }

        // --- Add or Edit Book
        composable(
            "addOrEditBook/{bookJson}",
            arguments = listOf(navArgument("bookJson") { type = NavType.StringType })
        ) { backStackEntry ->
            val bookJson = backStackEntry.arguments?.getString("bookJson") ?: ""
            val book = Json.decodeFromString<Book>(
                URLDecoder.decode(bookJson, StandardCharsets.UTF_8.toString())
            )
            AddOrEditBookScreen(book = book, navController = navController)
        }

        // --- Audiobook Detail
        composable(
            route = "audiobookDetail/{bookJson}",
            arguments = listOf(navArgument("bookJson") { type = NavType.StringType })
        ) { backStackEntry ->
            val bookJson = backStackEntry.arguments?.getString("bookJson") ?: ""
            val bookState = produceState<Book?>(initialValue = null, bookJson) {
                value = Json.decodeFromString(bookJson)
            }
            bookState.value?.let { book ->
                AudiobookDetailScreen(book = book, navController = navController)
            }
        }
    }
}