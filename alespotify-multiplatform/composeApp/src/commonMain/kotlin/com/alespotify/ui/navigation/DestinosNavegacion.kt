package com.alespotify.ui.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class DestinosNavegacion(
    val route: String,
    val title: String,
    arguments: List<NamedNavArgument> = listOf()
) {
    object LoginScreen : DestinosNavegacion("loginScreen", "loginScreen")
    object app : DestinosNavegacion("app", "app")
    object login : DestinosNavegacion(
        "login/{email}/{password}",
        "login",
        arguments = listOf(
            navArgument("email") { type = NavType.StringType },
            navArgument("password") { type = NavType.StringType }
        )
    )
    object android : DestinosNavegacion("android", "DatosAndroid")
}