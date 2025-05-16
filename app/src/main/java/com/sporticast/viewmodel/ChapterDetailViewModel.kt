package com.sporticast.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sporticast.model.Chapter
import com.sporticast.screens.data.api.RetrofitService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChapterDetailViewModel : ViewModel() {
    private val _chapter = MutableStateFlow<Chapter?>(null)
    val chapter = _chapter.asStateFlow()

    fun loadChapter(id: Int) {
        viewModelScope.launch {
            try {
                _chapter.value = RetrofitService.playerApi.getChapterById(id)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
