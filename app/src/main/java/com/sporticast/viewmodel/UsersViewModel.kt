package com.sporticast.viewmodel

import androidx.lifecycle.ViewModel
import com.sporticast.model.Users
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UsersViewModel : ViewModel() {
    private val _users = MutableStateFlow(
        listOf(
            Users(1, "Alice", "alice@example.com", role = "admin", createdAt = "2024-01-01"),
            Users(2, "Bob", "bob@example.com", role = "user", createdAt = "2024-01-05"),
            Users(3, "Charlie", "charlie@example.com", role = "user", createdAt = "2024-02-10")
        )
    )
    val users: StateFlow<List<Users>> = _users
}
