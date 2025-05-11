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

    fun loadUser() {
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
    fun deletedUser(id: Int, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            try {
                val response = RetrofitService.adminManagerApi.deleteUser(id)
                if (response.isSuccessful) {
                    onResult(true, "Xóa người dùng thành công")
                    loadUser()
                } else {
                    onResult(false, "Lỗi: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                onResult(false, "Lỗi mạng: ${e.localizedMessage}")
            }
        }
    }
    init {
        loadUser()
    }

}
