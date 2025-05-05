package com.sporticast.screens.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sporticast.model.Users
import com.sporticast.viewmodel.UsersViewModel

@Composable
fun UsersScreen(viewModel: UsersViewModel = viewModel()) {
    val users by viewModel.users.collectAsState()

    LazyColumn(modifier = Modifier.fillMaxSize().padding(8.dp)) {
        items(users) { user ->
            UserCard(user)
        }
    }
}

@Composable
fun UserCard(user: Users) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "👤 ${user.name}", style = MaterialTheme.typography.titleMedium)
            Text(text = "📧 ${user.email}")
            Text(text = "🛡 Vai trò: ${user.role}")
            Text(text = "📅 Tạo ngày: ${user.createdAt}")
        }
    }
}
