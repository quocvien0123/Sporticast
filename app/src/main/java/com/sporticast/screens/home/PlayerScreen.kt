package com.sporticast.screens.home

import android.media.MediaPlayer
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sporticast.R
import com.sporticast.ui.theme.colorLg_Rg
import kotlinx.coroutines.delay
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerScreen(
    title: String,
    author: String,
    duration: String,
    audioUrl: String,
    navController: NavController
) {
    var isPlaying by remember { mutableStateOf(false) }
    var sliderPosition by remember { mutableStateOf(0f) }
    var timeElapsed by remember { mutableStateOf(0) }
    var dynamicDuration by remember { mutableStateOf("00:00") }

    val rotation = remember { Animatable(0f) }

    // Decode text
    fun decodeText(text: String): String {
        return URLDecoder.decode(text, StandardCharsets.UTF_8.toString())
    }

    // Tạo MediaPlayer
    val mediaPlayer = remember {
        MediaPlayer().apply {
            try {
                setDataSource(audioUrl)
                prepare()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Dọn dẹp khi rời màn hình
    DisposableEffect(Unit) {
        onDispose {
            if (mediaPlayer.isPlaying) mediaPlayer.stop()
            mediaPlayer.release()
        }
    }

    // Cập nhật thời gian và thanh slider khi đang phát
    LaunchedEffect(isPlaying) {
        while (isPlaying) {
            delay(1000)
            timeElapsed = mediaPlayer.currentPosition / 1000
            sliderPosition = timeElapsed.toFloat() / (mediaPlayer.duration / 1000f)
            val minutes = timeElapsed / 60
            val seconds = timeElapsed % 60
            dynamicDuration = String.format("%02d:%02d", minutes, seconds)
        }
    }

    // Xoay ảnh khi phát
    LaunchedEffect(isPlaying) {
        if (isPlaying) {
            rotation.animateTo(
                targetValue = 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(5000, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                )
            )
        } else {
            rotation.stop()
            rotation.snapTo(0f)
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colors = colorLg_Rg)),
        topBar = {
            TopAppBar(
                title = { Text(text = "Đang phát", color = Color.White, fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        containerColor = Color.Transparent
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_round),
                contentDescription = "Book Cover",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(250.dp)
                    .clip(CircleShape)
                    .rotate(rotation.value)
            )

            Spacer(modifier = Modifier.height(50.dp))

            Text(
                text = "Tác phẩm: ${decodeText(title)}",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Tác giả: ${decodeText(author)}", color = Color.LightGray, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = dynamicDuration,
                color = Color.White,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Slider(
                value = sliderPosition,
                onValueChange = {
                    sliderPosition = it
                    val newTime = (it * (mediaPlayer.duration / 1000)).toInt()
                    mediaPlayer.seekTo(newTime * 1000)
                    timeElapsed = newTime
                },
                valueRange = 0f..1f,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(50.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        val rewind = (mediaPlayer.currentPosition - 10000).coerceAtLeast(0)
                        mediaPlayer.seekTo(rewind)
                        timeElapsed = rewind / 1000
                    },
                    modifier = Modifier.size(60.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Replay10,
                        contentDescription = "Rewind 10s",
                        tint = Color.White,
                        modifier = Modifier.size(50.dp)
                    )
                }

                IconButton(
                    onClick = {
                        isPlaying = !isPlaying
                        if (isPlaying) {
                            mediaPlayer.start()
                        } else {
                            mediaPlayer.pause()
                        }
                    },
                    modifier = Modifier.size(80.dp)
                ) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = "Play/Pause",
                        tint = Color.White,
                        modifier = Modifier.size(80.dp)
                    )
                }

                IconButton(
                    onClick = {
                        val forward = (mediaPlayer.currentPosition + 10000)
                            .coerceAtMost(mediaPlayer.duration)
                        mediaPlayer.seekTo(forward)
                        timeElapsed = forward / 1000
                    },
                    modifier = Modifier.size(60.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Forward10,
                        contentDescription = "Forward 10s",
                        tint = Color.White,
                        modifier = Modifier.size(50.dp)
                    )
                }
            }
        }
    }
}
