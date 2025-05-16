package com.sporticast.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.sporticast.ui.theme.colorLg_Rg
import com.sporticast.viewmodel.AuthViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerifyCodeScreen(
    navController: NavController,
    email: String,
    authViewModel: AuthViewModel = viewModel()
) {
    val codeState = remember { mutableStateOf("") }
    val isLoading by authViewModel::isLoading
    val errorMessage by authViewModel::errorMessage
    val scope = rememberCoroutineScope()
    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = Color.White,
        unfocusedTextColor = Color.White.copy(alpha = 0.7f),
        cursorColor = Color.White,
        focusedLabelColor = Color.White,
        unfocusedLabelColor = Color.LightGray,
        focusedPlaceholderColor = Color.LightGray,
        unfocusedPlaceholderColor = Color.LightGray
    )

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = { Text("🔐 Xác minh Email", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Brush.verticalGradient(colorLg_Rg)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("📧 Mã xác minh đã được gửi đến $email", color = Color.White, fontSize = 16.sp)
                Spacer(Modifier.height(20.dp))

                OutlinedTextField(
                    value = codeState.value,
                    onValueChange = {
                        if (it.length <= 5) codeState.value = it
                    },
                    label = { Text("Nhập mã xác minh") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = textFieldColors,
                )



                Spacer(Modifier.height(20.dp))

                Button(
                    onClick = {
                        scope.launch {
                            authViewModel.verifyOtpCode(
                                email = email,
                                code = codeState.value,
                                onSuccess = {
                                    navController.navigate("homeScreen") {
                                        popUpTo("loginScreen") { inclusive = true }
                                    }
                                }
                            )
                        }
                    },
                    enabled = codeState.value.length == 5 && !isLoading,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
                    } else {
                        Text("Xác minh")
                    }
                }

                errorMessage?.let {
                    Spacer(Modifier.height(12.dp))
                    Text(it, color = Color.Red)
                }
            }
        }
    }
}
