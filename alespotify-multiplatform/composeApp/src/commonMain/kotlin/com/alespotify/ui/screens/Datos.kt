package com.alespotify.ui.screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable


@Composable
fun DatosScreen(email : String, password : String){
    Text("$email -- $password")
}