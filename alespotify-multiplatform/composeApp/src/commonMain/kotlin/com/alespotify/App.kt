package com.alespotify

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.alespotify.model.Cancion
import com.alespotify.ui.ApiClient
import com.alespotify.ui.navigation.AppViewModel
import com.alespotify.ui.navigation.DestinosNavegacion
import com.alespotify.ui.navigation.LoginViewModel
import com.alespotify.ui.navigation.NavGraph
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json


@Composable
fun App() {
    val apiClient = ApiClient()
    var canciones by remember { mutableStateOf<List<Cancion>>(emptyList()) }
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute =
        currentBackStackEntry?.destination?.route ?: DestinosNavegacion.LoginScreen.route
    val loginViewModel = LoginViewModel()
    val appViewModel = AppViewModel()
    LaunchedEffect(Unit) {
        scope.launch {

        }
    }

    MaterialTheme {
        Surface {
            NavGraph(navController, loginViewModel, appViewModel )
        }
    }
}