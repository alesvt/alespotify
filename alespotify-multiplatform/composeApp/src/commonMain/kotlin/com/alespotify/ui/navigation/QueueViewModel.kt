package com.alespotify.ui.navigation

import androidx.lifecycle.ViewModel
import com.alespotify.model.Cancion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class QueueViewModel : ViewModel() {

    private val _currentSong = MutableStateFlow<Cancion?>(null)
    val currentSong: StateFlow<Cancion?> = _currentSong

    private val _songCalled = MutableStateFlow<Cancion?>(null)
    val songCalled = _songCalled.asStateFlow()


}