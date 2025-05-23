package com.sporticast.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateMapOf
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

class BookViewModel : ViewModel() {
    private val _loadBook = MutableStateFlow<List<Book>>(emptyList())
    val loadBook: StateFlow<List<Book>> = _loadBook.asStateFlow()

    private val _updateResult = MutableStateFlow<String?>(null)
    val updateResult: StateFlow<String?> = _updateResult

    private val bookApi = RetrofitService.bookApi
    private val _favoriteStates = mutableStateMapOf<Long, Boolean>() // bookId -> true/false
    val favoriteStates: Map<Long, Boolean> = _favoriteStates


    private val _favoriteBooks = mutableStateOf<List<Book>>(emptyList())
    val favoriteBooks: State<List<Book>> = _favoriteBooks

    fun toggleFavourite(userId: Long, bookId: Long, onResult: (Boolean, Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val isCurrentlyFavourite = _favoriteStates[bookId] == true

                if (isCurrentlyFavourite) {
                    val response = bookApi.removeFromFavorites(userId, bookId)
                    if (response.isSuccessful) {
                        _favoriteStates[bookId] = false
                        _favoriteBooks.value = _favoriteBooks.value.filterNot { it.id == bookId }
                        onResult(true, false) // success, now it's not favorite
                    } else {
                        onResult(false, isCurrentlyFavourite)
                    }
                } else {
                    val response = bookApi.addToFavorites(userId, bookId)
                    if (response.isSuccessful) {
                        _favoriteStates[bookId] = true
                        onResult(true, true) // success, now it's favorite
                    } else {
                        onResult(false, isCurrentlyFavourite)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                onResult(false, _favoriteStates[bookId] == true)
            }
        }
    }



    fun loadFavorites(userId: Long) {
        viewModelScope.launch {
            try {
                val response = bookApi.getFavorites(userId)
                if (response.isSuccessful) {
                    val favorites = response.body() ?: emptyList()
                    _favoriteBooks.value = favorites
                    _favoriteStates.clear()
                    favorites.forEach { book ->
                        _favoriteStates[book.id] = true
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }



    fun isFavorite(bookId: Long): Boolean = _favoriteStates[bookId] == true

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
                        language = dto.language,
                        chapterLimit = dto.chapterLimit,
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

    fun addToFavorites(userId: Long?, bookId: Long, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val response = bookApi.addToFavorites(userId, bookId)
                val responseBody = response.body()?.string() // Hoặc bất kỳ cách nào để lấy body từ response
                Log.d("API_Response", responseBody.toString()) // Xem chi tiết dữ liệu trả về

                if (response.isSuccessful) {
                    _favoriteStates[bookId] = true
                    onResult(true)
                } else {
                    onResult(false)
                }
            } catch (e: Exception) {
                onResult(false)
            }
        }
    }

    init {
        loadBook()
    }
}
