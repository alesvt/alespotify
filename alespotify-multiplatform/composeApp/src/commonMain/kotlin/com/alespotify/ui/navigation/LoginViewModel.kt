package com.alespotify.ui.navigation

import androidx.compose.runtime.State
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


}