package com.alespotify.ui.navigation


open class DestinosNavegacion(
    val route: String,
    val title: String,
) {

    object LoginScreen : DestinosNavegacion("loginScreen", "loginScreen")
    object App : DestinosNavegacion("app", "app")
    object RegisterScreen : DestinosNavegacion("registerScreen", "registerScreen")

}