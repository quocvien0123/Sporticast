package com.sporticast.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sporticast.dto.request.admin.ChapterRequest
import com.sporticast.ui.theme.colorLg_Rg
import com.sporticast.viewmodel.AudiobookManageViewModel
import kotlinx.coroutines.launch

@Composable
fun AudiobookManageScreen(
    audiobookId: Int,
    viewModel: AudiobookManageViewModel = viewModel()
) {
    var newLimit by remember { mutableStateOf("") }
    var chapterTitle by remember { mutableStateOf("") }
    var chapterAudioUrl by remember { mutableStateOf("") }
    var chapterDuration by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.loadData(audiobookId)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colorLg_Rg))
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = "📘 Sách: ${viewModel.audiobookTitle}",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = "🔢 Giới hạn chương hiện tại: ${viewModel.chapterLimit}",
                color = Color.LightGray
            )
            Text(
                text = "🧮 Số chương đã có: ${viewModel.chapterCount}",
                color = Color.LightGray
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = newLimit,
                onValueChange = { newLimit = it },
                label = { Text("Đặt lại giới hạn chương", color = Color.LightGray) },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color.White,
                    focusedBorderColor = Color(0xFFBB86FC),
                    unfocusedBorderColor = Color.Gray,
                    cursorColor = Color.White
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            Button(
                onClick = {
                    newLimit.toIntOrNull()?.let {
                        viewModel.updateLimit(audiobookId, it)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFBB86FC))            ) {
                Text("Cập nhật giới hạn", color = Color.White)
            }

            Divider(
                Modifier.padding(vertical = 16.dp),
                color = Color.Gray,
                thickness = 1.dp
            )

            Text(
                text = "🎧 Thêm chương mới",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = chapterTitle,
                onValueChange = { chapterTitle = it },
                label = { Text("Tên chương", color = Color.LightGray) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color.White,
                    focusedBorderColor = Color(0xFFBB86FC),
                    unfocusedBorderColor = Color.Gray,
                    cursorColor = Color.White
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = chapterAudioUrl,
                onValueChange = { chapterAudioUrl = it },
                label = { Text("URL audio", color = Color.LightGray) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color.White,
                    focusedBorderColor = Color(0xFFBB86FC),
                    unfocusedBorderColor = Color.Gray,
                    cursorColor = Color.White
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = chapterDuration,
                onValueChange = { chapterDuration = it },
                label = { Text("Thời lượng (giây)", color = Color.LightGray) },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color.White,
                    focusedBorderColor = Color(0xFFBB86FC),
                    unfocusedBorderColor = Color.Gray,
                    cursorColor = Color.White
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            Button(
                onClick = {
                    val duration = chapterDuration.toIntOrNull() ?: 0
                    viewModel.addChapter(
                        audiobookId,
                        ChapterRequest(chapterTitle, chapterAudioUrl, duration)
                    )
                },
                modifier = Modifier.fillMaxWidth(),
colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFBB86FC))            ) {
                Text("Thêm chương", color = Color.White)
            }

            Spacer(Modifier.height(16.dp))

            if (viewModel.message.isNotEmpty()) {
                Text(viewModel.message, color = Color.Red)
            }
        }
    }
}