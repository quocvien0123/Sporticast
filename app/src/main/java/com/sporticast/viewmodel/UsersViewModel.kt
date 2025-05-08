package com.sporticast.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sporticast.dto.request.User
import com.sporticast.model.Book
import com.sporticast.model.Users
import com.sporticast.screens.data.api.RetrofitService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UsersViewModel : ViewModel() {
    private val _loadUser = MutableStateFlow<List<User>>(emptyList())
    val loadUser: StateFlow<List<User>> = _loadUser.asStateFlow()

    private fun loadUser() {
        viewModelScope.launch {
            try {
                val response = RetrofitService.adminManagerApi.getAllUsers()
                _loadUser.value = response.map { dto ->
                    User(
                        id = dto.id,
                        name = dto.name,
                        email = dto.email,
                        is_admin = dto.is_admin,
                        avatar = dto.avatar,
                        createdAt =  dto.createdAt,
                        updatedAt = dto.updatedAt,
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    init {
        loadUser()
    }

//    private val _users = MutableStateFlow(
//        listOf(
//            Users(
//                id = 1,
//                name = "Alice",
//                email = "alice@example.com",
//                avatar = "https://i.pravatar.cc/150?img=1",
//                role = "admin",
//                createdAt = "2024-01-01"
//            ),
//            Users(
//                id = 2,
//                name = "Bob",
//                email = "bob@example.com",
//                avatar = "https://i.pravatar.cc/150?img=2",
//                role = "user",
//                createdAt = "2024-01-05"
//            ),
//            Users(
//                id = 3,
//                name = "Charlie",
//                email = "charlie@example.com",
//                avatar = "https://i.pravatar.cc/150?img=3",
//                role = "user",
//                createdAt = "2024-02-10"
//            )
//        )
//    )
//    val users: StateFlow<List<Users>> = _users
}
