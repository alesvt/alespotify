package com.alespotify.ui.screens

import androidx.compose.runtime.Composable
import com.alespotify.ui.navigation.AppViewModel
import com.alespotify.ui.navigation.QueueViewModel


@Composable
actual fun Player(
    isPlaying: Boolean,
    onPlayPauseClick: () -> Unit,
    onCollapseClick: () -> Unit,
    appViewModel: AppViewModel,
    queueViewModel: QueueViewModel
) {
}