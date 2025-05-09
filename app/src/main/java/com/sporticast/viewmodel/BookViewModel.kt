package com.sporticast.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sporticast.dto.request.BookRequest
import com.sporticast.model.Book
import com.sporticast.screens.data.api.RetrofitService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class   BookViewModel: ViewModel() {
    private val _loadBook = MutableStateFlow<List<Book>>(emptyList())
    val loadBook: StateFlow<List<Book>> = _loadBook.asStateFlow()

    fun loadBook() {
        viewModelScope.launch {
            try {
                val response = RetrofitService.adminManagerApi.getAllAudiobooks()
                _loadBook.value = response.map { dto ->
                    Book(
                        id = dto.id,
                        title = dto.title,
                        author = dto.author,
                        duration = dto.duration,
                        imageUrl = dto.imageUrl,
                        rating = dto.rating,
                        listenCount = dto.listenCount,
                        category = dto.category,
                        description = dto.description,
                        audioUrl = dto.audioUrl,
                        language = dto.language
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
  
    fun deleteBook(bookId: Int) {
        viewModelScope.launch {
            try {
               // RetrofitService.adminManagerApi.deleteBook(bookId)// chưa viet api
                _loadBook.value = _loadBook.value.filterNot { it.id == bookId }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    init {
        loadBook()
    }
}