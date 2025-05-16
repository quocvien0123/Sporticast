package com.sporticast.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sporticast.dto.response.UserResponse
import com.sporticast.screens.data.api.RetrofitService
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class ProfileViewModel : ViewModel() {
    var userInfo by mutableStateOf<UserResponse?>(null)
        private set

    fun fetchUserInfo(userId: Long) {
        viewModelScope.launch {
            try {
                val response = RetrofitService.userApi.getUserById(userId)
                if (response.isSuccessful) {
                    userInfo = response.body()
                }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}
