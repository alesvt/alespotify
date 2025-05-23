package com.alespotify.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.alespotify.shared.ApiService
import com.alespotify.ui.navigation.AppViewModel
import com.alespotify.ui.navigation.LoginViewModel
import com.alespotify.ui.navigation.QueueViewModel

@Composable
actual fun DatosScreen(
    navController: NavHostController,
    appViewModel: AppViewModel,
    loginViewModel: LoginViewModel,
    queueViewModel: QueueViewModel,
    apiService: ApiService
) {
}