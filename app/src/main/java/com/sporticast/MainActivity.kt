package com.sporticast

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sporticast.screens.home.HomeScreen
import com.sporticast.screens.LoginScreen
import com.sporticast.screens.ProfileScreen
import com.sporticast.screens.RegisterScreen
import com.sporticast.screens.WelcomeScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigator()
        }
    }
}

@Composable
fun AppNavigator() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "welcomeScreen") {
        composable("profile") {
            ProfileScreen(navController)
        }

        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }
        composable("homeScreen") { HomeScreen(navController) }
        composable("welcomeScreen") { WelcomeScreen(navController) }
    }
}


