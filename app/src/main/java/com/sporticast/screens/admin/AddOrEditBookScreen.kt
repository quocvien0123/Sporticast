package com.sporticast.screens.admin

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

@Composable
fun AddOrEditBookScreen(
    book: Book? = null,
    onSave: (BookRequest) -> Unit = {}
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
    var id by remember { mutableStateOf(book?.id ?: "") }

    var snackbarMessage by remember { mutableStateOf("") }
    var showSnackbar by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colorLg_Rg))
            .padding(16.dp)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    text = "📚 Thông tin sách",
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
                        if (title.isEmpty() || author.isEmpty() || category.isEmpty() || audioUrl.isEmpty() ||
                            description.isEmpty() || duration.isEmpty() || imageUrl.isEmpty() || language.isEmpty()
                        ) {
                            snackbarMessage = "Vui lòng điền đầy đủ thông tin"
                            showSnackbar = true
                        } else {
                            val bookRequest = BookRequest(
                                title = title,
                                author = author,
                                category = category,
                                audioUrl = audioUrl,
                                description = description,
                                duration = duration,
                                imageUrl = imageUrl,
                                language = language,
                                listenCount = listenCount,
                                rating = rating.toFloat(),
                              //  id = id
                            )

                            coroutineScope.launch {
                                try {
                                    val response =
                                        RetrofitService.adminManagerApi.addAudiobook(bookRequest)
                                    snackbarMessage = if (response.isSuccessful) {
                                        "✅ Sách đã được lưu thành công"
                                    } else {
                                        "❌ Lỗi khi lưu sách: ${response.message()}"
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
                    Text("💾 Lưu sách")
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
