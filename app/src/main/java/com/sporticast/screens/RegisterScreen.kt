package com.sporticast.screens
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import com.sporticast.ui.theme.colorLg_Rg

@Composable
fun RegisterScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val backgroundGradient = Brush.verticalGradient(colors = colorLg_Rg)


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGradient),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(
                    Color.White.copy(alpha = 0.05f)
                )
                .border(
                    width = 1.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.3f),
                            Color.Transparent
                        )
                    ),
                    shape = RoundedCornerShape(24.dp)
                )
                .padding(24.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_launcher_round),
                    contentDescription = "Logo",
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .size(80.dp)
                        .background(Color(0xFF004d40), shape = CircleShape)
                        .padding(12.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color(0xFF00E676))) {
                            append("Sposti")
                        }
                        withStyle(style = SpanStyle(color = Color.White)) {
                            append("Cash")
                        }
                    },
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(24.dp))

                // TextFields
                val tfModifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)

                val tfColors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF00E676),
                    unfocusedBorderColor = Color.Gray,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White.copy(0.6f),
                    focusedLeadingIconColor = Color.White,
                    unfocusedLeadingIconColor = Color.White
                )

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    placeholder = { Text("Name", color = Color.White) },
                    leadingIcon = {
                        Icon(Icons.Outlined.Person, contentDescription = null)
                    },
                    colors = tfColors,
                    modifier = tfModifier,
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text("Email",color = Color.White) },
                    leadingIcon = {
                        Icon(Icons.Outlined.Email, contentDescription = null)
                    },
                    colors = tfColors,
                    modifier = tfModifier,
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text("Password",color = Color.White) },
                    leadingIcon = {
                        Icon(Icons.Outlined.Lock, contentDescription = null)
                    },
                    visualTransformation = PasswordVisualTransformation(),
                    colors = tfColors,
                    modifier = tfModifier,
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    placeholder = { Text("Confirm Password",color = Color.White) },
                    leadingIcon = {
                        Icon(Icons.Outlined.Lock, contentDescription = null)
                    },
                    visualTransformation = PasswordVisualTransformation(),
                    colors = tfColors,
                    modifier = tfModifier,
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = { /* Handle register */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00E676),
                        contentColor = Color.White
                    )
                ) {
                    Text("Register", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    buildAnnotatedString {
                        append("Already have an account? ")
                        withStyle(SpanStyle(color = Color(0xFF00E676), fontWeight = FontWeight.Bold)) {
                            append("Login")
                        }
                    },
                    color = Color.White,
                    fontSize = 14.sp,
                    modifier = Modifier.clickable {
                        navController.navigate("login")
                    }
                )
            }
        }
    }
}
