package com.alespotify.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import androidx.navigation.compose.composable
import com.alespotify.ui.screens.DatosScreen
import com.alespotify.ui.screens.LoadingDataScreen
// import com.alespotify.ui.screens.DatosScreen
import com.alespotify.ui.screens.LoginScreen
import kotlin.math.log


@Composable
fun NavGraph(
    navController: NavHostController,
    loginViewModel: LoginViewModel,
    appViewModel: AppViewModel
) {
    NavHost(
        navController = navController,
        startDestination = DestinosNavegacion.LoginScreen.route,
    ) {
        composable(DestinosNavegacion.LoginScreen.route) {
            LoginScreen(
                navController = navController,
                loginViewModel = loginViewModel,
                appViewModel,
                onLoginSuccess = {
                    navController.navigate(DestinosNavegacion.load.route)
                }
            )
        }
        composable(DestinosNavegacion.load.route) {
            LoadingDataScreen(
                navController = navController,
                appViewModel = appViewModel,
                loginViewModel
            )
        }
        composable(DestinosNavegacion.app.route) {
            DatosScreen(
                navController = navController,
                appViewModel,
                loginViewModel
            )
        }

        composable(DestinosNavegacion.android.route) {
            //s DatosAndroid()
        }

    }
}

