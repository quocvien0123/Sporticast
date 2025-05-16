package com.sporticast.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import com.sporticast.model.Chapter

@Composable
fun ChapterList(
    chapters: List<Chapter>,
    onChapterClick: (Chapter) -> Unit // <- thêm callback
) {
    if (chapters.isEmpty()) {
        Text("Không có chương nào.", color = Color.LightGray, fontSize = 16.sp)
    } else {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(chapters) { chapter ->
                Text(
                    text = "▶ ${chapter.title}",
                    color = Color.Cyan,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .padding(vertical = 6.dp)
                        .clickable { onChapterClick(chapter) } // <- xử lý click
                )
            }
        }
    }
}

