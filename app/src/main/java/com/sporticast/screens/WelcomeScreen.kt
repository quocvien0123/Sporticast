package com.sporticast.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Headset
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sporticast.R
import com.sporticast.ui.theme.colorLg_Rg

@Composable
fun WelcomeScreen(navController: NavController) {


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colors = colorLg_Rg))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Animated logo
            var logoScale by remember { mutableStateOf(0f) }
            val animatedScale by animateFloatAsState(
                targetValue = logoScale,
                animationSpec = tween(1000)
            )
            
            LaunchedEffect(Unit) {
                logoScale = 1f
            }

            Image(
                painter = painterResource(id = R.drawable.ic_launcher_round),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(120.dp)
                    .scale(animatedScale)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Sposticast",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Welcome to Sposticast",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "AudioBox allowing you to listen to your favourite books anytime, anywhere",
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                color = Color.White.copy(alpha = 0.8f),
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = { navController.navigate("login") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2ECC71)
                ),
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(
                    text = "Get Started",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Already have an account?",
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = Color.White.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(
                onClick = { navController.navigate("register") },
                modifier = Modifier
                    .clip(RoundedCornerShape(30.dp))
            ) {
                Text(
                    text = "Sign In",
                    color = Color(0xFF2ECC71),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(
                onClick = { /* TODO: Sign in with username */ },
                modifier = Modifier
                    .clip(RoundedCornerShape(30.dp))
            ) {
                Text(
                    text = "Sign in with audibox username",
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 14.sp,
                )
            }
        }
    }
}
