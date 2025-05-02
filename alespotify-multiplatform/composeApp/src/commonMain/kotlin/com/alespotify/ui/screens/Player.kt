package com.alespotify.ui.screens

import androidx.compose.runtime.Composable
import com.alespotify.ui.navigation.AppViewModel

// commonMain
@Composable
expect fun Player(
    isPlaying: Boolean,
    onPlayPauseClick: () -> Unit,
    onCollapseClick: () -> Unit,
    appViewModel: AppViewModel
)