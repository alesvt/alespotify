package com.alespotify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.alespotify.model.player.AndroidMediaPlayer
import com.alespotify.model.player.MediaPlayer


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AndroidMediaPlayer.getInstance().initialize(this)
        setContent {
            App()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        AndroidMediaPlayer.getInstance().release()
    }
}

