package com.sporticast.viewmodel

import androidx.lifecycle.ViewModel
import com.sporticast.model.Users
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UsersViewModel : ViewModel() {
    private val _users = MutableStateFlow(
        listOf(
            Users(
                id = 1,
                name = "Alice",
                email = "alice@example.com",
                avatar = "https://i.pravatar.cc/150?img=1",
                role = "admin",
                createdAt = "2024-01-01"
            ),
            Users(
                id = 2,
                name = "Bob",
                email = "bob@example.com",
                avatar = "https://i.pravatar.cc/150?img=2",
                role = "user",
                createdAt = "2024-01-05"
            ),
            Users(
                id = 3,
                name = "Charlie",
                email = "charlie@example.com",
                avatar = "https://i.pravatar.cc/150?img=3",
                role = "user",
                createdAt = "2024-02-10"
            )
        )
    )
    val users: StateFlow<List<Users>> = _users
}
