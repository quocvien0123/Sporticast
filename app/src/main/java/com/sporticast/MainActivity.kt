package com.sporticast

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sporticast.model.Book
import com.sporticast.screens.home.HomeScreen
import com.sporticast.screens.LoginScreen
import com.sporticast.screens.ProfileScreen
import com.sporticast.screens.RegisterScreen
import com.sporticast.screens.WelcomeScreen
import com.sporticast.screens.home.AudiobookDetailScreen
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
    NavHost(navController, startDestination = "welcomeScreen") {
        composable("profile") {
            ProfileScreen(navController)
        }
        composable("audiobookDetail/{bookId}") { backStackEntry ->
            val viewModel: HomeViewModel = viewModel()
            val bookId = backStackEntry.arguments?.getString("bookId") ?: ""
            val book = viewModel.featuredBooks.value.find { it.id == bookId }


            if (book != null) {
                AudiobookDetailScreen(book = book as Book, navController = navController)
            }
        }

        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }
        composable("homeScreen") { HomeScreen(navController) }
        composable("welcomeScreen") { WelcomeScreen(navController) }
    }
}


