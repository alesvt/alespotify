package com.alespotify.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.alespotify.shared.ApiService
import com.alespotify.ui.screens.DatosScreen
// import com.alespotify.ui.screens.DatosScreen
import com.alespotify.ui.screens.LoginScreen
import com.alespotify.ui.screens.Player
import com.alespotify.ui.screens.RegisterScreen
import kotlin.math.log


@Composable
fun NavGraph(
    navController: NavHostController,
    loginViewModel: LoginViewModel = LoginViewModel(),
    appViewModel: AppViewModel = AppViewModel(),
    queueViewModel: QueueViewModel = QueueViewModel()
) {
    NavHost(
        navController = navController,
        startDestination = DestinosNavegacion.LoginScreen.route,
    ) {
        composable(DestinosNavegacion.LoginScreen.route) {
            LoginScreen(
                navController = navController,
                loginViewModel = loginViewModel,
                appViewModel = appViewModel,
                onLoginSuccess = {
                    navController.navigate(DestinosNavegacion.App.route)
                }
            )
        }
        composable(DestinosNavegacion.RegisterScreen.route) {
            RegisterScreen(
                navController = navController,
                loginViewModel = loginViewModel,
                onRegistrationSuccess = {
                    navController.navigate(DestinosNavegacion.App.route){
                        popUpTo(DestinosNavegacion.LoginScreen.route){
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(DestinosNavegacion.App.route) {
            DatosScreen(
                navController = navController,
                appViewModel = appViewModel,
                loginViewModel = loginViewModel,
                queueViewModel = queueViewModel,
                apiService = ApiService()
            )
        }


    }
}

