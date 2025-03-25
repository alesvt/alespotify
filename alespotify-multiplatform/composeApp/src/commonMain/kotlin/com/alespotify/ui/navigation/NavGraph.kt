package com.alespotify.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import androidx.navigation.compose.composable
import com.alespotify.ui.screens.DatosScreen
import com.alespotify.ui.screens.LoginScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = DestinosNavegacion.LoginScreen.route,
    ) {
        composable(DestinosNavegacion.LoginScreen.route) { LoginScreen(navController) }
        composable(DestinosNavegacion.app.route) { /*APP(TODOS LOS PARAMETROS QUE LLEVE)*/ }
        composable(DestinosNavegacion.login.route) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val password = backStackEntry.arguments?.getString("password") ?: ""
            DatosScreen(email, password)
            /* login funcion (llamada api y tal) */
        }
    }
}