package com.sporticast.screens.admin

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.sporticast.model.Book
import com.sporticast.viewmodel.BookViewModel
import kotlinx.serialization.json.Json

@Composable
fun BooksScreen(viewModel: BookViewModel = viewModel(),
                navController: NavController)  {
    LaunchedEffect(Unit) {
        viewModel.loadBook()
    }
    val books = viewModel.loadBook.collectAsState().value

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(books) { book ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF1E1E2F)
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
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
                        ) {

                            Text(
                                text = book.title,
                                color = Color.White,
                                fontSize = 18.sp
                            )
                            Text(
                                text = book.author,
                                color = Color.Gray,
                                fontSize = 14.sp
                            )
                            Text(
                                text = book.category,
                                color = Color.Gray,
                                fontSize = 14.sp
                            )
                            Text(
                                text = book.language,
                                color = Color.Gray,
                                fontSize = 14.sp
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Timer,
                                    contentDescription = null,
                                    tint = Color.Gray,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = book.duration, color = Color.Gray, fontSize = 12.sp)

                                Spacer(modifier = Modifier.width(8.dp))
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = Color.Yellow,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = book.rating.toString(), color = Color.Gray, fontSize = 12.sp)

                                Spacer(modifier = Modifier.width(8.dp))
                                Icon(
                                    imageVector = Icons.Default.Headphones,
                                    contentDescription = null,
                                    tint = Color.Gray,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = book.listenCount.toString(), color = Color.Gray, fontSize = 12.sp)
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            // URL hoặc mô tả ngắn gọn
                            Text(
                                text = "Audio: ${book.audioUrl}",
                                color = Color.LightGray,
                                fontSize = 12.sp,
                                maxLines = 2
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                horizontalArrangement = Arrangement.End,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "Sửa",
                                    color = Color(0xFF64B5F6),
                                    modifier = Modifier
                                        .clickable { /* TODO: Gọi viewModel.editBook(book) */
                                            val bookJson = Uri.encode(Json.encodeToString(Book.serializer(), book))
                                            navController.navigate("addOrEditBook/$bookJson")}
                                        .padding(horizontal = 8.dp)
                                )
                                Text(
                                    text = "Xóa",
                                    color = Color(0xFFEF5350),
                                    modifier = Modifier
                                        .clickable { /* TODO: Gọi viewModel.deleteBook(book.id) */
                                        }
                                        .padding(horizontal = 8.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Nút thêm sách dạng FAB
        FloatingActionButton(
            onClick = { /* TODO: Điều hướng tới màn hình thêm sách */
                navController.navigate("addOrEditBook")

            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = Color(0xFF64B5F6),
            contentColor = Color.White
        ) {
            Icon(Icons.Default.Add, contentDescription = "Thêm sách")
        }
    }
}
