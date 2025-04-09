package com.sporticast.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Headset
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun WelcomeScreen(navController: NavController) {
    //val background = painterResource(id = R.drawable.background_image)

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Headset,
                contentDescription = "Logo",
                tint = Color(0xFF2ECC71),
                modifier = Modifier.size(60.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Sposticast",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Welcome to Sposticast",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )

            Text(
                text = "AudioBox allowing you to listen to your favourite books anytime, anywhere",
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                color = Color.Black,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = { /* TODO: Get Started action */ },
                colors = ButtonDefaults.buttonColors( containerColor  = Color(0xFF2ECC71)),
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = "Get Started",
                    color = Color.White,
                    fontSize = 16.sp,
                    modifier = Modifier.clickable {
                        navController.navigate("login")
                    })
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Already have an account?",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.Black
            )

            TextButton(onClick = { /* TODO: Sign in */ }) {
                Text(
                    text = "Sign In",
                    color = Color.Green,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable{
                        navController.navigate("register")
                    }
                )
            }

            TextButton(onClick = { /* TODO: Sign in with username */ }) {
                Text(
                    text = "Sign in with audibox username",
                    color = Color.Black,
                    fontSize = 12.sp,

                )
            }
        }
    }
}
