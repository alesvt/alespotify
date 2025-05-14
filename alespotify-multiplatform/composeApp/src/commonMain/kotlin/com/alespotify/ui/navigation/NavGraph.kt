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
import com.alespotify.ui.screens.DatosScreen
import com.alespotify.ui.screens.LoadingDataScreen
// import com.alespotify.ui.screens.DatosScreen
import com.alespotify.ui.screens.LoginScreen
import com.alespotify.ui.screens.Player
import kotlin.math.log


@Composable
fun NavGraph(
    navController: NavHostController,
    loginViewModel: LoginViewModel = LoginViewModel(),
    appViewModel: AppViewModel = AppViewModel()
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
                    navController.navigate(DestinosNavegacion.load.route)
                }
            )


        }
        composable(DestinosNavegacion.load.route) {

            LoadingDataScreen(
                navController = navController,
                appViewModel = appViewModel,
                loginViewModel = loginViewModel
            )
        }
        composable(DestinosNavegacion.app.route) {
            println("---")
            println(loginViewModel.nose.value)
            // sale "no actualizado", que es el valor que le doy en el propio viewmodel,
            // antes de actualizarlo dentro del viewmodel
            DatosScreen(
                navController = navController,
                appViewModel = appViewModel,
                loginViewModel = loginViewModel
            )
        }

        composable(DestinosNavegacion.android.route) {
            //s DatosAndroid()
        }

    }
}

