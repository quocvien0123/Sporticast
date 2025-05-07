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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.KeyboardType
import com.sporticast.dto.request.BookRequest
import com.sporticast.model.Book
import com.sporticast.ui.theme.colorLg_Rg

@Composable
fun AddOrEditBookScreen(
    book: Book? = null,
    onSave: (BookRequest) -> Unit
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

    LazyColumn(
        modifier = Modifier
            .background(Brush.verticalGradient(colorLg_Rg))
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text("📚 Thông tin sách", color = Color.Transparent)
        }
@Composable
        fun inputCard(label: String, value: String, onChange: (String) -> Unit, keyboardType: KeyboardType = KeyboardType.Text, height: Int? = null) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                OutlinedTextField(
                    value = value,
                    onValueChange = onChange,
                    label = { Text(label) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .let { if (height != null) it.height(height.dp) else it },
                    keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                    shape = RoundedCornerShape(10.dp)
                )
            }
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
            inputCard("Lượt nghe", listenCount.toString(), {
                listenCount = it.toIntOrNull() ?: 0
            }, keyboardType = KeyboardType.Number)
        }

        item {
            inputCard("Đánh giá", rating.toString(), {
                rating = it.toDoubleOrNull() ?: 0.0
            }, keyboardType = KeyboardType.Decimal)
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
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
                        id = id
                    )
                    onSave(bookRequest)
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
}
