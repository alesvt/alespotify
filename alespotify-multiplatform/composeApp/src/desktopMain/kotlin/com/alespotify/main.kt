package com.alespotify

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowDecoration
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import uk.co.caprica.vlcj.player.base.MediaPlayer

fun main() = application {
    val state = rememberWindowState(
        width = 1100.dp,
        height = 700.dp
    )

    Window(
        onCloseRequest = ::exitApplication,
        // icon = ,
        state = state,
        title = "Alespotify",
        // decoration = WindowDecoration.Undecorated()

    ) {
        App()
    }
}