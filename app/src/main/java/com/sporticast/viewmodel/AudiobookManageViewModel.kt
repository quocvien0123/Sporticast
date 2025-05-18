package com.sporticast.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sporticast.dto.request.admin.ChapterLimitRequest
import com.sporticast.dto.request.admin.ChapterRequest
import com.sporticast.screens.data.api.RetrofitService
import kotlinx.coroutines.launch

class AudiobookManageViewModel:ViewModel() {
    var chapterLimit by mutableStateOf(0)
    var chapterCount by mutableStateOf(0)
    var message by mutableStateOf("")
    var audiobookTitle by mutableStateOf("")

    fun loadData(audiobookId: Int) {
        viewModelScope.launch {
            try {
                val bookRes = RetrofitService.adminManagerApi.getAudiobook(audiobookId)
                val countRes = RetrofitService.adminManagerApi.getChapterCount(audiobookId)

                if (bookRes.isSuccessful) {
                    val book = bookRes.body()!!
                    audiobookTitle = book.title
                    chapterLimit = book.chapterLimit
                }

                if (countRes.isSuccessful) {
                    chapterCount = countRes.body() ?: 0
                }
            } catch (e: Exception) {
                message = "❌ Lỗi tải dữ liệu: ${e.message}"
            }
        }
    }

    fun updateLimit(audiobookId: Int, limit: Int) {
        viewModelScope.launch {
            try {
                val res = RetrofitService.adminManagerApi.updateChapterLimit(audiobookId, ChapterLimitRequest(limit))
                if (res.isSuccessful) {
                    chapterLimit = limit
                    message = "✅ Cập nhật giới hạn thành công"
                } else {
                    message = "❌ Lỗi: ${res.code()}"
                }
            } catch (e: Exception) {
                message = "❌ Lỗi: ${e.message}"
            }
        }
    }

    fun addChapter(audiobookId: Int, chapter: ChapterRequest) {
        viewModelScope.launch {
            if (chapterCount >= chapterLimit) {
                message = "🚫 Đã đạt giới hạn chương"
                return@launch
            }

            try {
                val res = RetrofitService.adminManagerApi.addChapter(audiobookId, chapter)
                if (res.isSuccessful) {
                    chapterCount++
                    message = "✅ Thêm chương thành công"
                } else {
                    message = "❌ Lỗi: ${res.code()}"
                }
            } catch (e: Exception) {
                message = "❌ Lỗi: ${e.message}"
            }
        }
    }
}
