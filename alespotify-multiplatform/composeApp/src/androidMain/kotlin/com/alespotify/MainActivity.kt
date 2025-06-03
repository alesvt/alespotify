package com.alespotify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.alespotify.model.player.AndroidMediaPlayer
import com.alespotify.model.player.MediaPlayer


class MainActivity : ComponentActivity() {
    private lateinit var mediapl: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mediapl = AndroidMediaPlayer().apply {
            initialize(this@MainActivity)
        }


        setContent {
            App()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediapl.release()
    }
}

