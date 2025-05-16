package com.sporticast.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sporticast.model.Chapter
import com.sporticast.screens.data.api.RetrofitService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChapterViewModel: ViewModel() {
    private val _chapterList = MutableStateFlow<List<Chapter>>(emptyList())
    val chapterList = _chapterList.asStateFlow()

    fun loadChapter(audiobookId: String) {
    viewModelScope.launch {
        try {
            val chapters = RetrofitService.playerApi.getChapterByAudio(audiobookId.toString())
            _chapterList.value = chapters
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

}