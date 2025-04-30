package com.sporticast

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
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
import com.sporticast.screens.home.AudiobookDetailScreen
import com.sporticast.screens.home.PlayerScreen

import com.sporticast.viewmodel.HomeViewModel


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
    NavHost(navController, startDestination = "homeScreen") { // chinh lai wellcomeScreen sau khi hoan thanh
        composable("profile") {
            ProfileScreen(navController)
        }
        composable("home") {
            HomeScreen(navController)
        }
        composable("favorites") {
            FavoritesScreen(navController)
        }
        composable(
            route = "player/{title}/{author}/{duration}",
            arguments = listOf(
                navArgument("title") { type = NavType.StringType },
                navArgument("author") { type = NavType.StringType },
                navArgument("duration") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") ?: ""
            val author = backStackEntry.arguments?.getString("author") ?: ""
            val duration = backStackEntry.arguments?.getString("duration") ?: ""

            PlayerScreen(title = title, author = author, duration = duration, navController = navController)
        }
        composable("audiobookDetail/{bookId}") { backStackEntry ->
            val viewModel: HomeViewModel = viewModel()
            val bookId = backStackEntry.arguments?.getString("bookId") ?: ""
            val book = viewModel.featuredBooks.value.find { it.id == bookId }


            if (book != null) {
                AudiobookDetailScreen(book = book as Book, navController = navController)
            }
        }

//        composable("login") { LoginScreen(navController) }
//        composable("register") { RegisterScreen(navController) }
        composable("homeScreen") { HomeScreen(navController) }
//        composable("welcomeScreen") { WelcomeScreen(navController) }
    }
}


