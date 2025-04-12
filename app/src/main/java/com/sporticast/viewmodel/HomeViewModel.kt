package com.sporticast.viewmodel

import androidx.lifecycle.ViewModel
import com.sporticast.model.Category
import com.sporticast.model.Book
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

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

    private fun loadCategories() {
        _categories.value = listOf(
            Category("Novel", "📚"),
            Category("Business", "💼"), 
            Category("Psychology", "🧠"),
            Category("Science", "🔬"),
            Category("History", "⏳"),
            Category("Growth", "🌟"),
            Category("Literature", "✍️"),
            Category("Children", "👶")
        )
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
                1000
            ),
            Book(
                "2",
                "Tuna Đai Dương",
                "Paulo Tuna",
                "4 hours 30 minutes",
                "https://example.com/image1.jpg",
                4.5f,
                1000
            ),
            Book(
                "3",
                "How to Win Friends and Influence People",
                "Dale Carnegie",
                "6 hours 15 minutes", 
                "https://example.com/image2.jpg",
                4.8f,
                1500
            ),
            Book(
                "4",
                "I See Yellow Flowers on the Green Grass",
                "Nguyen Nhat Anh",
                "5 hours 45 minutes",
                "https://example.com/image3.jpg",
                4.7f,
                1200
            )
        )
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        // TODO: Implement search functionality
    }

    fun onCategorySelected(category: Category) {
        // TODO: Implement category selection
    }

    fun onBookSelected(book: Book) {
        // TODO: Implement book selection
    }
}