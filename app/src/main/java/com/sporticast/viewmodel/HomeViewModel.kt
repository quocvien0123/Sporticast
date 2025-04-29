package com.sporticast.viewmodel

import androidx.lifecycle.ViewModel
import com.sporticast.model.Category
import com.sporticast.model.Book
import com.sporticast.screens.data.api.RetrofitService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.viewModelScope
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
            "Novel" -> "📚"
            "Business" -> "💼"
            "Psychology" -> "🧠"
            "Science" -> "🔬"
            "History" -> "⏳"
            "Growth" -> "🌟"
            "Literature" -> "✍️"
            "Children" -> "👶"
            else -> "📚" // Icon mặc định
        }
    }



    private fun loadFeaturedBooks() {
        _featuredBooks.value = listOf(
            Book(
                "1",
                "The Alchemist",
                "Paulo Coelho",
                "4 hours 30 minutes",
                "https://example.com/image1.jpg",
                4.5f,
                1000,
                "Novel"
            ),
            Book(
                "2",
                "Tuna Đai Dương",
                "Paulo Tuna",
                "4 hours 30 minutes",
                "https://example.com/image1.jpg",
                4.5f,
                1000,
                "Novel"
            ),
            Book(
                "3",
                "How to Win Friends and Influence People",
                "Dale Carnegie",
                "6 hours 15 minutes",
                "https://example.com/image2.jpg",
                4.8f,
                1500,
                "Business"
            ),
            Book(
                "4",
                "I See Yellow Flowers on the Green Grass",
                "Nguyen Nhat Anh",
                "5 hours 45 minutes",
                "https://example.com/image3.jpg",
                4.7f,
                1200,
                "Children"
            )
        )
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        // TODO: Implement search functionality
    }



    fun onBookSelected(book: Book) {
        // TODO: Implement book selection
    }
}