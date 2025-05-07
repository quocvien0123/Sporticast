package com.sporticast.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sporticast.App
import com.sporticast.dto.request.LoginRequest
import com.sporticast.screens.data.api.RetrofitService
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    // Lấy sharedPreferences từ context của ứng dụng
    private val sharedPreferences = App.instance.getContext()
        .getSharedPreferences("auth", Context.MODE_PRIVATE)

    fun login(
        onAdminSuccess: () -> Unit,
        onUserSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            try {
                val response = RetrofitService.loginApi.loginUser(LoginRequest(email, password))
                val body = response.body()

                if (response.isSuccessful && body?.message == "Login Success") {
                    // ✅ Lưu token vào SharedPreferences
                    val prefs = App.instance.getSharedPreferences("auth", Context.MODE_PRIVATE)
                    prefs.edit().putString("jwt_token", body.token).apply()

                    if (body.is_admin) {
                        onAdminSuccess()
                    } else {
                        onUserSuccess()
                    }
                } else {
                    errorMessage = body?.message ?: "Login failed"
                }
            } catch (e: Exception) {
                errorMessage = "Network error: ${e.localizedMessage}"
            } finally {
                isLoading = false
            }
        }
    }

    fun getToken(): String? {
        val prefs = App.instance.getSharedPreferences("auth", Context.MODE_PRIVATE)
        return prefs.getString("jwt_token", null)
    }


    fun isLoggedIn(): Boolean {
        return getToken() != null
    }

    fun logout(onComplete: () -> Unit) {
        val prefs = App.instance.getSharedPreferences("auth", Context.MODE_PRIVATE)
        prefs.edit().remove("jwt_token").apply()
        onComplete()
    }


}
