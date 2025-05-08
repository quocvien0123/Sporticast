package com.sporticast.screens.admin

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sporticast.dto.request.BookRequest
import com.sporticast.model.Book
import com.sporticast.screens.data.api.RetrofitService
import com.sporticast.ui.theme.colorLg_Rg
import kotlinx.coroutines.launch
import kotlin.math.log

@Composable
fun AddOrEditBookScreen(
    book: Book? = null,
    onSave: (BookRequest) -> Unit = {},

    ) {

    var title by remember { mutableStateOf(book?.title ?: "") }
    var author by remember { mutableStateOf(book?.author ?: "") }
    var category by remember { mutableStateOf(book?.category ?: "") }
    var audioUrl by remember { mutableStateOf(book?.audioUrl ?: "") }
    var description by remember { mutableStateOf(book?.description ?: "") }
    var duration by remember { mutableStateOf(book?.duration ?: "") }
    var imageUrl by remember { mutableStateOf(book?.imageUrl ?: "") }
    var rating by remember { mutableStateOf(book?.rating ?: 0.0) }
    var language by remember { mutableStateOf(book?.language ?: "") }
    var listenCount by remember { mutableStateOf(book?.listenCount ?: 0) }

    var snackbarMessage by remember { mutableStateOf("") }
    var showSnackbar by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colorLg_Rg))
            .padding(16.dp)
    ) {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            item {
                Text(
                    text = if (book != null) "✏️ Chỉnh sửa sách" else "📚 Thêm sách mới",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White
                )
            }

            @Composable
            fun inputCard(
                label: String,
                value: String,
                onChange: (String) -> Unit,
                keyboardType: KeyboardType = KeyboardType.Text,
                height: Int? = null
            ) {
                OutlinedTextField(
                    value = value,
                    onValueChange = onChange,
                    label = { Text(label) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .let { if (height != null) it.height(height.dp) else it },
                    keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                    shape = RoundedCornerShape(14.dp),
                    textStyle = LocalTextStyle.current.copy(
                        color = Color.White,
                        fontSize = 16.sp
                    )
                )
            }

            item { inputCard("Tiêu đề", title, { title = it }) }
            item { inputCard("Tác giả", author, { author = it }) }
            item { inputCard("Thể loại", category, { category = it }) }
            item { inputCard("Audio URL", audioUrl, { audioUrl = it }) }
            item { inputCard("Mô tả", description, { description = it }, height = 120) }
            item { inputCard("Thời lượng", duration, { duration = it }) }
            item { inputCard("Ảnh bìa (URL)", imageUrl, { imageUrl = it }) }
            item { inputCard("Ngôn ngữ", language, { language = it }) }

            item {
                inputCard(
                    label = "Lượt nghe",
                    value = listenCount.toString(),
                    onChange = { listenCount = it.toIntOrNull() ?: 0 },
                    keyboardType = KeyboardType.Number
                )
            }

            item {
                inputCard(
                    label = "Đánh giá",
                    value = rating.toString(),
                    onChange = { rating = it.toDoubleOrNull() ?: 0.0 },
                    keyboardType = KeyboardType.Decimal
                )
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = {
                        if (title.isBlank() || author.isBlank() || category.isBlank() ||
                            audioUrl.isBlank() || description.isBlank() || duration.isBlank() ||
                            imageUrl.isBlank() || language.isBlank()
                        ) {
                            snackbarMessage = "⚠️ Vui lòng điền đầy đủ thông tin"
                            showSnackbar = true
                        } else {
                            val bookRequest = BookRequest(
                                title,
                                author,
                                category,
                                audioUrl,
                                description,
                                duration,
                                imageUrl,
                                language,
                                listenCount,
                                rating.toFloat(),
                            )


                            coroutineScope.launch {
                                try {
                                    val response = if (book != null) {
                                        RetrofitService.adminManagerApi.updateBook(
                                            id = book.id,
                                            book = bookRequest
                                        )
                                    } else {
                                        RetrofitService.adminManagerApi.addAudiobook(bookRequest)
                                    }

                                    snackbarMessage = if (response.isSuccessful) {
                                        if (book != null) "✅ Đã cập nhật sách thành công"
                                        else "✅ Đã thêm sách thành công"
                                    } else {
                                        println("❌ Error Body: ${response.errorBody()?.string()}")
                                        "❌ Lỗi khi thực hiện yêu cầu"
                                    }
                                } catch (e: Exception) {
                                    snackbarMessage = "⚠️ Lỗi mạng: ${e.localizedMessage}"
                                }
                                showSnackbar = true
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(if (book != null) "💾 Cập nhật" else "➕ Thêm sách")
                }
            }
        }

        if (showSnackbar) {
            Snackbar(
                modifier = Modifier.padding(16.dp),
                action = {
                    TextButton(onClick = { showSnackbar = false }) {
                        Text("OK")
                    }
                }
            ) {
                Text(snackbarMessage)
            }
        }
    }
}
