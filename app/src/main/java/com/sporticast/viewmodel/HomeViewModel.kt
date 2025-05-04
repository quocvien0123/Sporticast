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

    private val _featuredBooks = MutableStateFlow<List<Book>>(emptyList())
    val featuredBooks: StateFlow<List<Book>> = _featuredBooks.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        loadCategories()
        loadFeaturedBooks()
    }
    private val _selectedCategory = MutableStateFlow<Category?>(null)
    val selectedCategory: StateFlow<Category?> = _selectedCategory.asStateFlow()

    fun onCategorySelected(category: Category) {
        _selectedCategory.value = if (_selectedCategory.value == category) null else category
    }


    private fun loadCategories() {
        viewModelScope.launch {
            try {
                val response = RetrofitService.categoryApi.getCategories()
                _categories.value = response.map { categoryFromDb: Category ->
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
        return when (name) {
            "Novel" -> "📖"      // Sách đang đọc, tinh tế hơn 📚
            "Business" -> "🏢"   // Toà nhà công ty
            "Psychology" -> "🧬" // DNA - tượng trưng cho trí tuệ, tinh tế hơn 🧠
            "Science" -> "⚛️"    // Biểu tượng nguyên tử
            "History" -> "🏺"    // Bình cổ Hy Lạp – đại diện cho lịch sử
            "Growth" -> "📈"     // Đồ thị tăng trưởng
            "Literature" -> "🖋️" // Bút máy – lịch thiệp hơn ✍️
            "Children" -> "🧒"   // Biểu tượng bé trai/gái thay 👶
            else -> "📘"         // Sách đóng – icon mặc định thanh lịch
        }
    }




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
                        category = dto.category
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                // Xử lý lỗi nếu cần
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        // TODO: Implement search functionality
    }
    fun getBookById(id: String): Book? {
        return _featuredBooks.value.find { it.id == id }
    }
    fun getBookByTitle(title: String): Book? {
        return _featuredBooks.value.find { it.title == title }
    }


    fun onBookSelected(book: Book) {
        // TODO: Implement book selection
    }
}