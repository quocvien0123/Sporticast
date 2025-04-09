
package com.sporticast

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sporticast.Screens.HomeScreen
import com.sporticast.Screens.LoginScreen
import com.sporticast.Screens.RegisterScreen
import com.sporticast.Screens.WelcomeScreen


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
        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }
        composable("homeScreen") { HomeScreen() }
        composable ("welcomeScreen"){ WelcomeScreen(navController) }
    }
}
