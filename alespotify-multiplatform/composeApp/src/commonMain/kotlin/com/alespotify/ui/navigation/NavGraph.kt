package com.alespotify.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import androidx.navigation.compose.composable
import com.alespotify.model.Artist
import com.alespotify.model.Cancion
import com.alespotify.model.User
import com.alespotify.ui.screens.DatosScreen
import com.alespotify.ui.screens.ImagenDesdeApiScreen
import com.alespotify.ui.screens.LoadingDataScreen
// import com.alespotify.ui.screens.DatosScreen
import com.alespotify.ui.screens.LoginScreen
import com.alespotify.ui.screens.MainView
import com.alespotify.ui.screens.PlayScreen
import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId


@Composable
fun NavGraph(navController: NavHostController, loginViewModel: LoginViewModel, appViewModel: AppViewModel) {
    NavHost(
        navController = navController,
        startDestination = DestinosNavegacion.LoginScreen.route,
    ) {
        composable(DestinosNavegacion.LoginScreen.route) {
            LoginScreen(
                navController = navController,
                loginViewModel = loginViewModel,
                onLoginSuccess = { user ->
                    println("Login exitoso en NavGraph, usuario: $user")
                    navController.navigate(DestinosNavegacion.load.route) { // Navega a la pantalla de carga
                        popUpTo(DestinosNavegacion.LoginScreen.route) { inclusive = true }
                    }
                }
            )
        }
        composable(DestinosNavegacion.load.route) {
            LoadingDataScreen(navController = navController, appViewModel = appViewModel)
        }
        composable(DestinosNavegacion.app.route) { // DatosScreen se muestra en esta ruta
            val songs by appViewModel.songs.collectAsState()
            val artists by appViewModel.artists.collectAsState()
            val playlists by appViewModel.playlists.collectAsState()

            DatosScreen(
                navController = navController,
                songs = songs,
                artists = artists,
                playlists = playlists
            )
        }
        composable(DestinosNavegacion.login.route) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val password = backStackEntry.arguments?.getString("password") ?: ""

            // hago la petición de login
            // si recibo codigo 200, paso el usuario (ahora paso cancion para probar)
            //Login()

            ImagenDesdeApiScreen()
            // DatosScreen()

            /* login funcion (llamada api y tal) */
        }
        composable(DestinosNavegacion.android.route) {
            //s DatosAndroid()
        }

    }
}

