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

    // Lấy SharedPreferences một lần
    private val prefs = App.instance.getSharedPreferences("auth", Context.MODE_PRIVATE)

    private fun saveAuthData(token: String, isAdmin: Boolean) {
        prefs.edit()
            .putString("jwt_token", token)
            .putBoolean("is_admin", isAdmin)
            .apply()
    }

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
                    // ✅ Lưu token và quyền admin
                    saveAuthData(body.token, body.is_admin)

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
        return prefs.getString("jwt_token", null)
    }

    fun isLoggedIn(): Boolean {
        return getToken() != null
    }

    fun isAdmin(): Boolean {
        return prefs.getBoolean("is_admin", false)
    }

    fun logout(onComplete: () -> Unit) {
        prefs.edit()
            .remove("jwt_token")
            .remove("is_admin")
            .apply()
        onComplete()
    }
}
