package com.sporticast.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sporticast.dto.request.BookRequest
import com.sporticast.model.Book
import com.sporticast.screens.data.api.RetrofitService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BookViewModel : ViewModel() {
    private val _loadBook = MutableStateFlow<List<Book>>(emptyList())
    val loadBook: StateFlow<List<Book>> = _loadBook.asStateFlow()

    private val _updateResult = MutableStateFlow<String?>(null)
    val updateResult: StateFlow<String?> = _updateResult

    private val bookApi = RetrofitService.bookApi

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

    fun addBook(dto: BookRequest, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            try {
                val response = RetrofitService.adminManagerApi.addAudiobook(dto)
                if (response.isSuccessful) {
                    onResult(true, "Thêm sách thành công")
                    loadBook()
                } else {
                    onResult(false, "Lỗi: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                onResult(false, "Lỗi mạng: ${e.localizedMessage}")
            }
        }
    }

    fun updateBook(id: Long, dto: BookRequest) {
        viewModelScope.launch {
            try {
                val response = RetrofitService.adminManagerApi.updateBook(id, dto)
                if (response.isSuccessful) {
                    val book = response.body()
                    _updateResult.value = "Cập nhật thành công: ${book?.title}"
                    loadBook()
                } else {
                    _updateResult.value = "Thất bại: ${response.errorBody()?.string()}"
                }
            } catch (e: Exception) {
                _updateResult.value = "Lỗi: ${e.localizedMessage}"
            }
        }
    }

    fun deleteBook(id: Long, onResult: () -> Unit) {
        viewModelScope.launch {
            try {
                val response = bookApi.deleteBook(id)
                if (response.isSuccessful) {
                    onResult()
                } else {
                    // xử lý lỗi nếu cần
                }
            } catch (e: Exception) {
                // xử lý lỗi nếu cần
            }
        }
    }



    init {
        loadBook()
    }
}
