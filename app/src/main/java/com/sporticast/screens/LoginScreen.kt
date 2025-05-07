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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sporticast.R
import com.sporticast.viewmodel.AuthViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sporticast.ui.theme.colorLg_Rg

@Composable
fun LoginScreen(navController: NavController, viewModel: AuthViewModel = viewModel()) {
    val backgroundGradient = Brush.verticalGradient(colors = colorLg_Rg)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGradient),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .background(Color.White.copy(alpha = 0.05f), RoundedCornerShape(20.dp))
                .border(
                    1.dp,
                    Brush.linearGradient(listOf(Color.White.copy(alpha = 0.3f), Color.Transparent)),
                    RoundedCornerShape(24.dp)
                )
                .padding(24.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painter = painterResource(R.drawable.ic_launcher_round),
                    contentDescription = "Logo",
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .size(80.dp)
                        .background(Color(0xFF004d40), CircleShape)
                        .padding(12.dp)
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    buildAnnotatedString {
                        append("Sposti")
                        withStyle(SpanStyle(color = Color.White)) { append("Cash") }
                    },
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF00C853)
                )

                Spacer(Modifier.height(60.dp))

                AuthTextField(
                    value = viewModel.email,
                    onValueChange = { viewModel.email = it },
                    label = "Email",
                    icon = Icons.Outlined.Email
                )

                Spacer(Modifier.height(12.dp))

                AuthTextField(
                    value = viewModel.password,
                    onValueChange = { viewModel.password = it },
                    label = "Password",
                    icon = Icons.Outlined.Lock,
                    isPassword = true
                )

                Spacer(Modifier.height(24.dp))

                Button(
                    onClick = {
                        viewModel.login(
                            onAdminSuccess = {
                                navController.navigate("adminScreen") {
                                    popUpTo(0) { inclusive = true }
                                }
                            },
                            onUserSuccess = {
                                navController.navigate("homeScreen") {
                                    popUpTo(0) { inclusive = true }
                                }
                            }
                        )
                    },
                    modifier = Modifier
                        .width(160.dp)
                        .height(48.dp),
                    shape = RoundedCornerShape(30.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00C853)),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp),
                    enabled = !viewModel.isLoading
                ) {
                    if (viewModel.isLoading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    } else {
                        Text("Login", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                    }
                }

                viewModel.errorMessage?.let {
                    Text(
                        it,
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Spacer(Modifier.height(16.dp))

                Text(
                    text = "Forgot the password?",
                    color = Color(0xFF00E676),
                    fontSize = 14.sp,
                    modifier = Modifier.clickable {
                        // TODO: Chuyển đến màn hình quên mật khẩu
                    }
                )

                Spacer(Modifier.height(24.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Divider(modifier = Modifier.weight(1f), color = Color.Gray)
                    Text(
                        text = "  or continue with  ",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    Divider(modifier = Modifier.weight(1f), color = Color.Gray)
                }

                Spacer(Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val buttonSize = 48.dp
                    listOf(
                        R.drawable.ic_facebook to "Facebook",
                        R.drawable.ic_google to "Google",
                        R.drawable.ic_apple to "Apple"
                    ).forEach { (iconRes, contentDesc) ->
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .background(Color.White, shape = RoundedCornerShape(12.dp))
                                .border(1.dp, Color.LightGray, shape = RoundedCornerShape(12.dp))
                                .clickable { /* TODO: Social login */ },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(iconRes),
                                contentDescription = contentDesc,
                                modifier = Modifier.size(48.dp),
                                tint = Color.Unspecified
                            )
                        }

                    }
                }

                Spacer(Modifier.height(24.dp))

                Text(
                    buildAnnotatedString {
                        append("Don't have an account? ")
                        withStyle(
                            SpanStyle(color = Color(0xFF00E676), fontWeight = FontWeight.Bold)
                        ) {
                            append("Sign up")
                        }
                    },
                    fontSize = 16.sp,
                    color = Color.White,
                    modifier = Modifier.clickable {
                        navController.navigate("registerScreen") {
                            popUpTo("loginScreen") { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun AuthTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: ImageVector,
    isPassword: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = Color.White) },
        leadingIcon = { Icon(icon, contentDescription = null, tint = Color.White) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color(0xFF00C853),
            unfocusedIndicatorColor = Color.Gray,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White
        ),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None
    )
}
