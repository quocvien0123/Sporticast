package com.sporticast.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.sporticast.Helper.TextToSpeechHelper
import com.sporticast.model.Book
import com.sporticast.viewmodel.BookViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


@Composable
fun FeaturedContentItem(
    book: Book,
    userId: Long?,
    onClick: () -> Unit,
    onFavouriteClick: (Long, Long) -> Unit,
    isFavourite: Boolean,
    bookViewModel: BookViewModel
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val isSpeaking = remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable {
                if (!isSpeaking.value) {
                    isSpeaking.value = true
                    coroutineScope.launch {
                        TextToSpeechHelper.speakWithFPT(
                            context,
                            "Bạn đang nghe sách ${book.title} của tác giả ${book.author}"
                                   ,
                            onComplete = { isSpeaking.value = false }
                        )
                    }
                    onClick()
                }
            },

                shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E2F))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                AsyncImage(
                    model = book.imageUrl,
                    contentDescription = "Ảnh bìa sách",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(12.dp))
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentHeight()
            ) {
                Text(
                    text = book.title,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 5,
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = book.author, color = Color.Gray, fontSize = 16.sp, maxLines = 1)
                Text(text = book.category, color = Color.Gray, fontSize = 16.sp, maxLines = 1)
                Spacer(modifier = Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Timer, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(book.duration, color = Color.Gray, fontSize = 12.sp)

                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFD700), modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(book.rating.toString(), color = Color.Gray, fontSize = 12.sp)

                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.Default.Headphones, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(book.listenCount.toString(), color = Color.Gray, fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Icon(
                imageVector = if (isFavourite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                contentDescription = "Favorite",
                tint = if (isFavourite) Color.Red else Color.Gray,
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xFF1E1E2F), shape = RoundedCornerShape(8.dp))
                    .padding(8.dp)
                    .align(Alignment.CenterVertically)
                    .clickable {
                        if (userId != null) {
                            onFavouriteClick(userId, book.id)
                            coroutineScope.launch {
                                val message = if (!isFavourite) {
                                    "Đã thêm ${book.title} vào danh sách yêu thích"


                                } else {
                                    "Đã xóa ${book.title} khỏi danh sách yêu thích"
                                }
                                TextToSpeechHelper.speakWithFPT(context, message)
                            }
                        }
                    }
            )
        }
    }
}
