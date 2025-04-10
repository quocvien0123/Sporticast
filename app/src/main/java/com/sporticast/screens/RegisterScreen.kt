package com.sporticast.screens
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sporticast.R
import com.sporticast.ui.theme.gradient

@Composable
fun RegisterScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colors = gradient)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.ic_launcher_round),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.height(12.dp))


            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color(0xFF1B5E20))) {
                        append("Sposti")
                    }
                    withStyle(style = SpanStyle(color = Color.White)) {
                        append("Cash")
                    }
                },
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(40.dp))


            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name", color = Color.White) },
                leadingIcon = {
                    Icon(Icons.Outlined.Person, contentDescription = null, tint = Color.White)
                },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(15.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color(0xFF064635),
                    unfocusedIndicatorColor = Color.Gray
                )
            )

            Spacer(modifier = Modifier.height(12.dp))


            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email", color = Color.White) },
                leadingIcon = {
                    Icon(Icons.Outlined.Email, contentDescription = null, tint = Color.White)
                },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(15.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color(0xFF064635),
                    unfocusedIndicatorColor = Color.Gray
                )
            )

            Spacer(modifier = Modifier.height(12.dp))


            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password", color = Color.White) },
                leadingIcon = {
                    Icon(Icons.Outlined.Lock, contentDescription = null, tint = Color.White)
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(15.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color(0xFF064635),
                    unfocusedIndicatorColor = Color.Gray
                ),
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(12.dp))


            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm Password", color = Color.White) },
                leadingIcon = {
                    Icon(Icons.Outlined.Lock, contentDescription = null, tint = Color.White)
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(15.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color(0xFF064635),
                    unfocusedIndicatorColor = Color.Gray
                ),
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(24.dp))


            Button(
                onClick = { /* Xử lý đăng ký */ },
                modifier = Modifier.width(150.dp),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF064635),
                    contentColor = Color.White
                )
            ) {
                Text("Register", fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))


            Text(

                text = buildAnnotatedString {
                    append("Already have an account? ")
                    withStyle(
                        style = SpanStyle(
                            color = Color(0xFF1B5E20),
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("Login")
                    }
                },
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier.clickable {
                    navController.navigate("login")
                }
            )
        }
    }
}