package com.alespotify.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.ui.draw.clip
import coil3.compose.rememberAsyncImagePainter

actual fun getPlatformName(): String = "Android"



@OptIn(ExperimentalResourceApi::class)
@Composable
fun DatosAndroid() {
    var sliderPosition by remember { mutableStateOf(38f) }

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {

        Image(
            painter = rememberAsyncImagePainter("placeholder.svg"),
            contentDescription = "Background Blur",
            modifier = Modifier
                .fillMaxSize()
                .blur(radius = 20.dp)
                .alpha(0.3f),
            contentScale = ContentScale.Crop
        )

        Column(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 48.dp, bottom = 24.dp, start = 24.dp, end = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Blinding Lights",
                    fontSize = 20.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "The Weeknd",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 40.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = rememberAsyncImagePainter(""),
                    contentDescription = "Album Cover",
                    modifier = Modifier
                        .aspectRatio(1f)
                        .fillMaxWidth(0.8f)
                        .background(Color.Transparent, shape = RoundedCornerShape(8.dp))
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Column(modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp)) {
                Slider(
                    value = sliderPosition,
                    onValueChange = { sliderPosition = it },
                    valueRange = 0f..100f,
                    colors = SliderDefaults.colors(
                        thumbColor = Color.White,
                        activeTrackColor = Color.White,
                        inactiveTrackColor = Color.White.copy(alpha = 0.3f)
                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "1:42", fontSize = 12.sp, color = Color.Gray)
                    Text(text = "4:22", fontSize = 12.sp, color = Color.Gray)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {}, modifier = Modifier.size(48.dp)) {
                        Icon(Icons.Filled.Share, contentDescription = "Shuffle", tint = Color.Gray)
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        IconButton(onClick = {}, modifier = Modifier.size(48.dp)) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Skip Back", tint = Color.White)
                        }
                        IconButton(onClick = {}, modifier = Modifier.size(56.dp).background(Color.White, shape = RoundedCornerShape(28.dp))) {
                            Icon(Icons.Filled.PlayArrow, contentDescription = "Pause", tint = Color.Black)
                        }
                        IconButton(onClick = {}, modifier = Modifier.size(48.dp)) {
                            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Skip Forward", tint = Color.White)
                        }
                    }

                    IconButton(onClick = {}, modifier = Modifier.size(48.dp)) {
                        Icon(Icons.Filled.PlayArrow, contentDescription = "Repeat", tint = Color.Gray)
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF1E1E1E))
                    .padding(vertical = 16.dp, horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BottomNavItem(icon = Icons.Filled.Home, label = "Home")
                BottomNavItem(icon = Icons.Filled.Search, label = "Search")
                BottomNavItem(icon = Icons.AutoMirrored.Filled.List, label = "Library")
            }
        }
    }
}

@Composable
fun BottomNavItem(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        IconButton(onClick = {}, modifier = Modifier.size(48.dp)) {
            Icon(icon, contentDescription = label, tint = Color.White)
        }
        Text(text = label, fontSize = 12.sp, color = Color.White)
    }
}