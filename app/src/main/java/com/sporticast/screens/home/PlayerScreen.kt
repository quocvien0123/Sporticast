package com.sporticast.screens.home

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.sporticast.Helper.TextToSpeechHelper
import com.sporticast.R
import com.sporticast.ui.theme.colorLg_Rg
import com.sporticast.viewmodel.ChapterViewModel
import kotlinx.coroutines.delay
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerScreen(
    title: String,
    duration: String,
    author: String,
    audioUrl: String,
    audiobookId: Int,
    navController: NavController,
    chapterViewModel: ChapterViewModel = viewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        TextToSpeechHelper.speakWithFPT(context, "Chúc bạn nghe sách vui vẻ")
    }

    fun decodeText(text: String): String {
        return URLDecoder.decode(text, StandardCharsets.UTF_8.toString())
    }

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(audioUrl))
            prepare()
        }
    }

    // Trạng thái UI liên quan ExoPlayer
    var isPrepared by remember { mutableStateOf(false) }
    val isPlayingState = remember { mutableStateOf(false) }
    var sliderPosition by remember { mutableStateOf(0f) }
    var timeElapsed by remember { mutableStateOf(0) }
    var totalDuration by remember { mutableStateOf(0) }
    val rotation = remember { Animatable(0f) }

    val chapters by chapterViewModel.chapterList.collectAsState()

    // Load danh sách chương
    LaunchedEffect(audiobookId) {

        chapterViewModel.loadChapter(audiobookId.toString())
    }

    // Listener cập nhật trạng thái ExoPlayer
    DisposableEffect(exoPlayer) {
        val listener = object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                isPrepared = (state == Player.STATE_READY)
                totalDuration = if (exoPlayer.duration > 0) (exoPlayer.duration / 1000).toInt() else 0
                if (state == Player.STATE_ENDED) {
                    isPlayingState.value = false
                }
            }
            override fun onIsPlayingChanged(isPlayingNow: Boolean) {
                isPlayingState.value = isPlayingNow
            }
        }
        exoPlayer.addListener(listener)
        onDispose {
            exoPlayer.removeListener(listener)
            exoPlayer.release()
        }
    }

    // Cập nhật slider và thời gian chạy
    LaunchedEffect(isPlayingState.value, isPrepared) {
        while (isPlayingState.value && isPrepared) {
            delay(1000)
            timeElapsed = (exoPlayer.currentPosition / 1000).toInt()
            if (totalDuration > 0) {
                sliderPosition = timeElapsed.toFloat() / totalDuration.toFloat()
            }
        }
    }

    // Animation xoay hình khi đang phát
    LaunchedEffect(isPlayingState.value) {
        if (isPlayingState.value) {
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
                title = {
                    Text(text = "Đang phát", color = Color.White, fontSize = 20.sp)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
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
                text = decodeText(title),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Tác giả: ${decodeText(author)}", color = Color.LightGray, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(24.dp))

            if (!isPrepared) {
                CircularProgressIndicator(color = Color.White)
                Text("Đang tải âm thanh...", color = Color.White)
                return@Column
            }

            Text(
                text = String.format(
                    "%02d:%02d / %02d:%02d",
                    timeElapsed / 60, timeElapsed % 60,
                    totalDuration / 60, totalDuration % 60
                ),
                color = Color.White,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Slider(
                value = sliderPosition,
                onValueChange = {
                    sliderPosition = it
                    if (isPrepared && totalDuration > 0) {
                        val newPosition = (it * totalDuration * 1000).toLong()
                        exoPlayer.seekTo(newPosition)
                        timeElapsed = (newPosition / 1000).toInt()
                    }
                },
                valueRange = 0f..1f,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(30.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        val rewind = (exoPlayer.currentPosition - 10000).coerceAtLeast(0)
                        exoPlayer.seekTo(rewind)
                        timeElapsed = (rewind / 1000).toInt()
                    },
                    modifier = Modifier.size(30.dp)
                ) {
                    Icon(Icons.Default.Replay10, contentDescription = "Rewind 10s", tint = Color.White, modifier = Modifier.size(50.dp))
                }

                IconButton(
                    onClick = {
                        if (exoPlayer.isPlaying) exoPlayer.pause() else exoPlayer.play()
                    },
                    modifier = Modifier.size(30.dp)
                ) {
                    Icon(
                        imageVector = if (isPlayingState.value) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = "Play/Pause",
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                }

                IconButton(
                    onClick = {
                        val forward = (exoPlayer.currentPosition + 10000).coerceAtMost(exoPlayer.duration)
                        exoPlayer.seekTo(forward)
                        timeElapsed = (forward / 1000).toInt()
                    },
                    modifier = Modifier.size(30.dp)
                ) {
                    Icon(Icons.Default.Forward10, contentDescription = "Forward 10s", tint = Color.White, modifier = Modifier.size(50.dp))
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Danh sách chương",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            ChapterList(
                chapters = chapters,
                onChapterClick = { selectedChapter ->
                    try {
                        exoPlayer.stop()
                        exoPlayer.clearMediaItems()
                        exoPlayer.setMediaItem(MediaItem.fromUri(selectedChapter.audioUrl))
                        exoPlayer.prepare()
                        exoPlayer.play()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            )
        }
    }
}
