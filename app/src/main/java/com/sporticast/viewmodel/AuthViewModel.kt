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
    val isVerified = mutableStateOf(false)

    // Lấy SharedPreferences một lần
    private val prefs = App.instance.getSharedPreferences("auth", Context.MODE_PRIVATE)

    private fun saveAuthData(token: String, isAdmin: Boolean, userId: Long) {
        prefs.edit()
            .putString("jwt_token", token)
            .putBoolean("is_admin", isAdmin)
            .putLong("user_id", userId)
            .apply()
    }


    fun login(
        onOtpRequired: (email: String) -> Unit,
        onAdminSuccess: () -> Unit,
        onUserSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            try {
                val response = RetrofitService.loginApi.loginUser(LoginRequest(email, password))
                val body = response.body()

                if (response.isSuccessful && body != null) {
                    when (body.status) {
                        "verify" -> {
                            body.email?.let { onOtpRequired(it) }
                        }
                        "success" -> {
                            saveAuthData(body.token, body.is_admin, body.user_id)
                            if (body.is_admin) onAdminSuccess() else onUserSuccess()
                        }
                        "error" -> {
                            errorMessage = body.message ?: "Login failed"
                        }
                        else -> {
                            errorMessage = body.message ?: "Login failed"
                        }
                    }
                } else {
                    val errorMsg = response.errorBody()?.string()
                    errorMessage = "Login failed"
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
    fun getUserId(): Long? {
        val id = prefs.getLong("user_id", -1)
        return if (id != -1L) id else null
    }

    fun verifyOtpCode(email: String, code: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            try {
                val response = RetrofitService.loginApi.verifyCode(email, code)
                if (response.code() == 200) {
                    onSuccess()
                } else if (response.code() == 400) {
                    errorMessage = response.body()?.message ?: "Mã không hợp lệ hoặc đã hết hạn"
                } else {
                    errorMessage = "Đã xảy ra lỗi không xác định: ${response.code()}"
                }

            } catch (e: Exception) {
                errorMessage = "Lỗi mạng: ${e.localizedMessage}"
            } finally {
                isLoading = false
            }
        }
    }


}
