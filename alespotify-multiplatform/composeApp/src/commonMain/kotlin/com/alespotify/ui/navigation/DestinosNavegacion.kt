package com.alespotify.ui.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

open class DestinosNavegacion(
    val route: String,
    val title: String,
) {

    object LoginScreen : DestinosNavegacion("loginScreen", "loginScreen")
    object App : DestinosNavegacion("app", "app")

    object Load : DestinosNavegacion("load", "load")



}