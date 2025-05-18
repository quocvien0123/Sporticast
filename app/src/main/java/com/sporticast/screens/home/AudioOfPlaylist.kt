//package com.sporticast.screens.home
//
//        import androidx.compose.foundation.Image
//        import androidx.compose.foundation.background
//        import androidx.compose.foundation.clickable
//        import androidx.compose.foundation.layout.*
//        import androidx.compose.foundation.lazy.LazyColumn
//        import androidx.compose.foundation.lazy.items
//        import androidx.compose.foundation.shape.RoundedCornerShape
//        import androidx.compose.material.icons.Icons
//        import androidx.compose.material.icons.filled.ArrowBack
//        import androidx.compose.material.icons.filled.MoreVert
//        import androidx.compose.material3.*
//        import androidx.compose.runtime.*
//        import androidx.compose.ui.Alignment
//        import androidx.compose.ui.Modifier
//        import androidx.compose.ui.draw.clip
//        import androidx.compose.ui.graphics.Brush
//        import androidx.compose.ui.graphics.Color
//        import androidx.compose.ui.layout.ContentScale
//        import androidx.compose.ui.res.painterResource
//        import androidx.compose.ui.text.font.FontWeight
//        import androidx.compose.ui.unit.dp
//        import androidx.compose.ui.unit.sp
//        import androidx.lifecycle.viewmodel.compose.viewModel
//        import androidx.navigation.NavController
//        import com.sporticast.R
//        import com.sporticast.ui.theme.colorLg_Rg
//        import com.sporticast.viewmodel.PlaylistViewModel
//        import kotlinx.serialization.encodeToString
//        import kotlinx.serialization.json.Json
//        import java.net.URLEncoder
//        import java.nio.charset.StandardCharsets
//
//@Composable
//        fun AudioOfPlaylist(
//            navController: NavController,
//            playlistViewModel: PlaylistViewModel = viewModel()
//        ) {
//            val playlists by playlistViewModel.audiobooks.collectAsState()
//            val snackbarHostState = remember { SnackbarHostState() }
//            val coroutineScope = rememberCoroutineScope()
//
//            Scaffold(
//                bottomBar = {
//                    BottomNavigationBar(navController)
//                },
//                containerColor = Color.Transparent,
//                snackbarHost = {
//                    SnackbarHost(
//                        hostState = snackbarHostState,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(top = 8.dp)
//                    )
//                }
//            ) { innerPadding ->
//                Column(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .background(Brush.verticalGradient(colors = colorLg_Rg))
//                        .padding(horizontal = 16.dp)
//                        .padding(top = 24.dp)
//                        .padding(innerPadding)
//                ) {
//                    // Top Bar
//                    Row(
//                        verticalAlignment = Alignment.CenterVertically,
//                        modifier = Modifier.fillMaxWidth()
//                    ) {
//                        IconButton(onClick = { navController.popBackStack() }) {
//                            Icon(
//                                imageVector = Icons.Default.ArrowBack,
//                                contentDescription = "Back",
//                                tint = Color.White
//                            )
//                        }
//
//                        Spacer(modifier = Modifier.width(8.dp))
//                        Text(
//                            text = "PlayList",
//                            color = Color.White,
//                            fontSize = 20.sp,
//                            modifier = Modifier.weight(1f)
//                        )
//
//                        Spacer(modifier = Modifier.weight(1f))
//
//                        IconButton(onClick = {}) {
//                            Icon(
//                                imageVector = Icons.Default.MoreVert,
//                                contentDescription = "More",
//                                tint = Color.White
//                            )
//                        }
//                    }
//
//                    Spacer(modifier = Modifier.height(24.dp))
//
//                    // Playlist List
//                    LazyColumn(
//                        modifier = Modifier.fillMaxSize(),
//                        verticalArrangement = Arrangement.spacedBy(16.dp)
//                    ) {
//                        items(playlists) { playlist ->
//                            Row(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .clip(RoundedCornerShape(12.dp))
//                                    .background(Color.White.copy(alpha = 0.1f))
//                                    .clickable {
//                                        val encoded = URLEncoder.encode(
//                                            Json.encodeToString(playlist),
//                                            StandardCharsets.UTF_8.toString()
//                                        )
//                                        navController.navigate("audiobookDetail/$encoded")
//                                    }
//                                    .padding(16.dp),
//                                verticalAlignment = Alignment.CenterVertically
//                            ) {
//                                Image(
//                                    painter = painterResource(id = R.drawable.ic_launcher_round), // Replace with actual image logic
//                                    contentDescription = playlist.title,
//                                    contentScale = ContentScale.Crop,
//                                    modifier = Modifier
//                                        .size(60.dp)
//                                        .clip(RoundedCornerShape(8.dp))
//                                )
//
//                                Spacer(modifier = Modifier.width(16.dp))
//
//                                Column {
//                                    Text(
//                                        text = playlist.title,
//                                        color = Color.White,
//                                        fontWeight = FontWeight.Bold,
//                                        fontSize = 18.sp
//                                    )
//                                    Spacer(modifier = Modifier.height(4.dp))
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//
//            LaunchedEffect(Unit) {
//                playlistViewModel.getAudiobooksInPlaylist(1)
//            }
//        }