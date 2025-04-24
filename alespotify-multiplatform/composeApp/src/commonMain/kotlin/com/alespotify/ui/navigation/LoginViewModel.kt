package com.alespotify.ui.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alespotify.model.User
import com.alespotify.shared.ApiService
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val apiService = ApiService()
    var loginResult by mutableStateOf<User?>(null)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set
    var isLoading by mutableStateOf(false)
        private set

    fun login(email: String, password: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            loginResult = null
            try {
                val user = apiService.login(email, password)
                loginResult = user
                if (user != null) {
                    // Manejar el éxito del login (e.g., actualizar estado, navegar)
                    println("Login successful: $user")

                } else {
                    errorMessage = "Credenciales incorrectas"
                }
            } catch (e: Exception) {

                errorMessage = "Error al realizar la petición de login: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }



}