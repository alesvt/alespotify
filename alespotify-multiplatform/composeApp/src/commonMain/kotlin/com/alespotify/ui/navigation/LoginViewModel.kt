package com.alespotify.ui.navigation

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alespotify.model.User
import com.alespotify.shared.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class LoginViewModel : ViewModel() {
    private val apiService = ApiService()

    private val _usuario = MutableStateFlow<User?>(null)
    val usuario: StateFlow<User?> = _usuario.asStateFlow()

    var loginResult by mutableStateOf<User?>(null)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set
    var isLoading by mutableStateOf(false)
        private set

    var registrationResult by mutableStateOf<User?>(null)
        private set

    fun login(email: String, password: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            loginResult = null
            try {
                val user = apiService.login(email, password)
                loginResult = user
                _usuario.value = loginResult
                _usuario.emit(user)
                if (user != null) {
                    _usuario.value = user
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

    fun registro(nombre: String, email: String, password: String, image: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            registrationResult = null
            try {
                val user = apiService.registerUser(nombre, email, password, image)
                println(user)
                registrationResult = user
                _usuario.value = registrationResult
                _usuario.emit(user)
                if (user != null) {
                    _usuario.value = user
                    println("user registered successfully")
                } else {
                    errorMessage = "Error. algo pasa"
                }
            } catch (e: Exception) {
                errorMessage = "Error al realizar la petición de registro: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }
}