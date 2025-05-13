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
import kotlinx.coroutines.launch


class LoginViewModel : ViewModel() {
    private val apiService = ApiService()

    private val _usuario = MutableStateFlow<User?>(null)
    val usuario: StateFlow<User?> = _usuario

    private val _nose = MutableStateFlow<String>("no actualizado")
    val nose: StateFlow<String> = _nose
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
            _nose.value = "hola"
            _nose.emit("adios")
            try {
                val user = apiService.login(email, password)
                loginResult = user
                _usuario.value = loginResult
                _usuario.emit(user)
                if (user != null) {
                    _usuario.value = user
                    // Manejar el éxito del login (e.g., actualizar estado, navegar)
                    println(_nose.value)
                    println("+++")
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