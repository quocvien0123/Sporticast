package  com.sporticast.screens.home
import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sporticast.R
import com.sporticast.ui.theme.colorLg_Rg
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    showProfileMenu: Boolean,
    showNotificationMenu: Boolean,
    showSettingsMenu: Boolean,
    onProfileMenuChange: (Boolean) -> Unit,
    onNotificationMenuChange: (Boolean) -> Unit,
    onSettingsMenuChange: (Boolean) -> Unit,
    onLogout: () -> Unit
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_round),
                    contentDescription = "App Icon",
                    modifier = Modifier
                        .size(40.dp)
                        .padding(end = 8.dp)
                        .clip(CircleShape)
                )
                Text(
                    text = "SpostiCash",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        actions = {
            // Notifications
            Box {
                IconButton(onClick = { onNotificationMenuChange(true) }) {
                    Icon(Icons.Default.Notifications, contentDescription = "Notifications", tint = Color.White)
                }
                DropdownMenu(
                    expanded = showNotificationMenu,
                    onDismissRequest = { onNotificationMenuChange(false) },
                    modifier = Modifier.background(
                        Brush.verticalGradient(colors = colorLg_Rg)
                    )
                ) {
                    DropdownMenuItem(
                        text = { Text("New notification", color = Color.White) },
                        onClick = { onNotificationMenuChange(false) }
                    )
                }
            }

            // Profile
            Box {
                IconButton(onClick = { onProfileMenuChange(true) }) {
                    Icon(Icons.Default.Person, contentDescription = "Profile", tint = Color.White)
                }
                DropdownMenu(
                    expanded = showProfileMenu,
                    onDismissRequest = { onProfileMenuChange(false) },
                    modifier = Modifier.background(
                        Brush.verticalGradient(colors = colorLg_Rg)
                    )
                ) {
                    DropdownMenuItem(
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Default.Person,
                                    contentDescription = "Profile",
                                    tint = Color.White,
                                    modifier = Modifier
                                        .size(20.dp)
                                        .padding(end = 8.dp)
                                )
                                Text("Profile", color = Color.White)
                            }
                        },
                        onClick = { onProfileMenuChange(false) }
                    )
                    DropdownMenuItem(
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Default.Logout,
                                    contentDescription = "Logout",
                                    tint = Color.White,
                                    modifier = Modifier
                                        .size(20.dp)
                                        .padding(end = 8.dp)
                                )
                                Text("Logout", color = Color.White)
                            }
                        },
                        onClick = {
                            onProfileMenuChange(false)
                            onLogout()
                        }
                    )
                }
            }

            // Settings
            Box {
                IconButton(onClick = { onSettingsMenuChange(true) }) {
                    Icon(Icons.Default.Settings, contentDescription = "Settings", tint = Color.White)
                }
                DropdownMenu(
                    expanded = showSettingsMenu,
                    onDismissRequest = { onSettingsMenuChange(false) },
                    modifier = Modifier.background(
                        Brush.verticalGradient(colors = colorLg_Rg)
                    )
                ) {
                    DropdownMenuItem(
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Default.Settings,
                                    contentDescription = "Settings",
                                    tint = Color.White,
                                    modifier = Modifier
                                        .size(20.dp)
                                        .padding(end = 8.dp)
                                )
                                Text("Settings", color = Color.White)
                            }
                        },
                        onClick = { onSettingsMenuChange(false) }
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        ),

    )
}
