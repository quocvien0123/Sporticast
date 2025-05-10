package com.sporticast.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sporticast.model.Category
import com.sporticast.model.Book
import com.sporticast.screens.data.api.RetrofitService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories.asStateFlow()

    private val _searchResults = MutableStateFlow<List<Book>>(emptyList())
    val searchResults: StateFlow<List<Book>> = _searchResults.asStateFlow()

    private val _featuredBooks = MutableStateFlow<List<Book>>(emptyList())
    val featuredBooks: StateFlow<List<Book>> = _featuredBooks.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow<Category?>(null)
    val selectedCategory: StateFlow<Category?> = _selectedCategory.asStateFlow()

    init {
        loadCategories()
        loadFeaturedBooks()
    }

    // ==== Danh mục ====
    private fun loadCategories() {
        viewModelScope.launch {
            try {
                val response = RetrofitService.categoryApi.getCategories()
                _categories.value = response.map { categoryFromDb ->
                    Category(
                        id = categoryFromDb.id,
                        name = categoryFromDb.name,
                        icon = getIconForCategory(categoryFromDb.name)
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

   private fun getIconForCategory(name: String): String {
        val categoryIcons = mapOf(
            "Novel" to "📖",
            "Business" to "🏢",
            "Psychology" to "🧬",
            "Science" to "⚛️",
            "History" to "🏺",
            "Growth" to "📈",
            "Literature" to "🖋️",
            "Children" to "🧒"
        )
        return categoryIcons[name] ?: "📘"
    }

    fun onCategorySelected(category: Category) {
        _selectedCategory.value = if (_selectedCategory.value == category) null else category
        clearSearch() // ✅ Reset tìm kiếm khi chọn danh mục
    }

    // ==== Sách nổi bật ====
    private fun loadFeaturedBooks() {
        viewModelScope.launch {
            try {
                val response = RetrofitService.bookApi.getBooks()
                _featuredBooks.value = response.map { dto ->
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

    // ==== Tìm kiếm ====
    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun performSearch() {
        val query = _searchQuery.value.trim()
        if (query.isBlank()) {
            _searchResults.value = emptyList()
            return
        }

        viewModelScope.launch {
            try {
                val response = RetrofitService.searchApi.searchAudiobooks(query)
                _searchResults.value = response.map { dto ->
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
                _searchResults.value = emptyList()
            }
        }
    }

    // ✅ Reset tìm kiếm
    fun clearSearch() {
        _searchQuery.value = ""
        _searchResults.value = emptyList()
    }

    // ==== Tiện ích lấy sách ====
    fun getBookById(id: String): Book? {
        return _featuredBooks.value.find { it.id.toString() == id }
    }

    fun getBookByTitle(title: String): Book? {
        return _featuredBooks.value.find { it.title == title }
    }

    fun onBookSelected(book: Book) {
        // TODO: Implement book selection logic
    }
}
