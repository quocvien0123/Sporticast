package com.sporticast.screens.admin

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.sporticast.dto.request.BookRequest
import com.sporticast.model.Book
import com.sporticast.screens.data.api.RetrofitService
import com.sporticast.ui.theme.colorLg_Rg
import com.sporticast.viewmodel.BookViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.log

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddOrEditBookScreen(
    book: Book? = null,
    navController: NavController,
    viewModel: BookViewModel = viewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    var title by remember { mutableStateOf(book?.title ?: "") }
    var author by remember { mutableStateOf(book?.author ?: "") }
    var category by remember { mutableStateOf(book?.category ?: "") }
    var audioUrl by remember { mutableStateOf(book?.audioUrl ?: "") }
    var description by remember { mutableStateOf(book?.description ?: "") }
    var duration by remember { mutableStateOf(book?.duration ?: "") }
    var imageUrl by remember { mutableStateOf(book?.imageUrl ?: "") }
    var language by remember { mutableStateOf(book?.language ?: "") }
    var listenCount by remember { mutableStateOf(book?.listenCount ?: 0) }
    var rating by remember { mutableFloatStateOf(book?.rating ?: 0f) }
    var showSnackbar by remember { mutableStateOf(false) }
    var snackbarMessage by remember { mutableStateOf("") }


    // Màu sắc cho TextField (Material 3)
    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = Color.White,
        unfocusedTextColor = Color.White,
        cursorColor = Color.White,
        focusedBorderColor = Color.Cyan,
        unfocusedBorderColor = Color.LightGray,
        focusedLabelColor = Color.Cyan,
        unfocusedLabelColor = Color.LightGray
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (book != null) "📘 Chỉnh sửa sách" else "📘 Thêm sách",
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        containerColor = Color.Transparent
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(colorLg_Rg))
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                val fieldModifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)

                @Composable
                fun label(text: String): @Composable () -> Unit = {
                    Text(text, color = Color.White, fontSize = 14.sp)
                }

                // Áp dụng màu sắc cho các TextField
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = label("Tiêu đề"),
                    modifier = fieldModifier,
                    colors = textFieldColors
                )
                OutlinedTextField(
                    value = author,
                    onValueChange = { author = it },
                    label = label("Tác giả"),
                    modifier = fieldModifier,
                    colors = textFieldColors
                )
                OutlinedTextField(
                    value = category,
                    onValueChange = { category = it },
                    label = label("Thể loại"),
                    modifier = fieldModifier,
                    colors = textFieldColors
                )
                OutlinedTextField(
                    value = audioUrl,
                    onValueChange = { audioUrl = it },
                    label = label("Audio URL"),
                    modifier = fieldModifier,
                    colors = textFieldColors
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = label("Mô tả"),
                    modifier = fieldModifier,
                    colors = textFieldColors
                )
                OutlinedTextField(
                    value = duration,
                    onValueChange = { duration = it },
                    label = label("Thời lượng"),
                    modifier = fieldModifier,
                    colors = textFieldColors
                )
                OutlinedTextField(
                    value = imageUrl,
                    onValueChange = { imageUrl = it },
                    label = label("Hình ảnh URL"),
                    modifier = fieldModifier,
                    colors = textFieldColors
                )
                OutlinedTextField(
                    value = language,
                    onValueChange = { language = it },
                    label = label("Ngôn ngữ"),
                    modifier = fieldModifier,
                    colors = textFieldColors
                )
                OutlinedTextField(
                    value = listenCount.toString(),
                    onValueChange = { listenCount = it.toIntOrNull() ?: 0 },
                    label = label("Lượt nghe"),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = fieldModifier,
                    colors = textFieldColors
                )

                Text("Đánh giá: ${rating.toInt()}⭐", color = Color.White, fontSize = 14.sp)
                Slider(
                    value = rating,
                    onValueChange = { rating = it },
                    valueRange = 0f..5f,
                    steps = 4,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (title.isBlank() || author.isBlank() || category.isBlank() || audioUrl.isBlank()
                            || description.isBlank() || duration.isBlank() || imageUrl.isBlank() || language.isBlank()
                        ) {
                            snackbarMessage = "⚠️ Vui lòng điền đầy đủ thông tin"
                            showSnackbar = true
                        } else {
                            val bookRequest = BookRequest(
                                title, author, category, audioUrl,
                                description, duration, imageUrl, language,
                                listenCount, rating
                            )
                            coroutineScope.launch {
                                if (book != null) {
                                    viewModel.updateBook(book.id, bookRequest)
                                    navController.previousBackStackEntry
                                        ?.savedStateHandle?.set("bookAddResult", "updated")
                                    navController.popBackStack()
                                } else {
                                    viewModel.addBook(bookRequest) { success, message ->
                                        snackbarMessage = if (success) "✅ $message" else "❌ $message"
                                        showSnackbar = true
                                        if (success) {
                                            navController.previousBackStackEntry
                                                ?.savedStateHandle?.set("bookAddResult", "success")
                                            navController.popBackStack()
                                        }
                                    }
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF264B5D))
                ) {
                    Text(if (book != null) "💾 Cập nhật" else "➕ Thêm sách", color = Color.White)
                }

                if (showSnackbar) {
                    Snackbar(
                        modifier = Modifier.padding(top = 8.dp),
                        containerColor = Color.White
                    ) {
                        Text(snackbarMessage, color = Color.Black)
                    }
                }
            }
        }
    }
}
