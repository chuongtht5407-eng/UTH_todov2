package com.example.uth_todov2.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uth_todov2.data.model.UserProfile
import com.example.uth_todov2.ui.components.BottomNavigationBar
import com.example.uth_todov2.ui.theme.PrimaryBlue
import com.example.uth_todov2.ui.theme.SecondaryText
import com.example.uth_todov2.ui.theme.SuccessGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    userProfile: UserProfile,
    onNavigate: (String) -> Unit,
    onLogout: () -> Unit,
    onAddTaskClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(text = "Hồ sơ", fontWeight = FontWeight.Bold, fontSize = 17.sp, color = Color(0xFF202124))
                    }
                },
                navigationIcon = {
                    TextButton(onClick = { onNavigate("home") }) {
                        Text(text = "Cài đặt", color = PrimaryBlue, fontSize = 14.sp)
                    }
                },
                actions = {
                    TextButton(onClick = { /* Edit profile */ }) {
                        Text(text = "Sửa", color = PrimaryBlue, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            BottomNavigationBar(
                currentRoute = "profile",
                onNavigate = onNavigate,
                onAddTaskClick = onAddTaskClick
            )
        },
        containerColor = Color(0xFFF9FAFB)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Avatar & Name (Compact)
            Box(
                modifier = Modifier.size(68.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(PrimaryBlue),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = userProfile.avatarInitials,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }

                // Camera Badge
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(PrimaryBlue)
                        .border(2.dp, Color.White, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = "Đổi ảnh đại diện",
                        tint = Color.White,
                        modifier = Modifier.size(12.dp)
                    )
                }
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = userProfile.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(0xFF202124)
                )
                Spacer(modifier = Modifier.height(1.dp))
                Text(
                    text = userProfile.email,
                    fontSize = 12.sp,
                    color = SecondaryText
                )
            }

            // Google Linked Chip
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFFE8F5E9)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = SuccessGreen, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Đã liên kết tài khoản Google", fontSize = 11.sp, color = SuccessGreen, fontWeight = FontWeight.Medium)
                }
            }

            // 3 Stat Cards Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StatCard(value = "${userProfile.completedTasksCount}", label = "Việc đã xong", modifier = Modifier.weight(1f))
                StatCard(value = "${userProfile.streakDays} ngày", label = "Chuỗi liên tiếp", modifier = Modifier.weight(1f))
                StatCard(value = "${userProfile.onTimeRate}%", label = "Đúng hạn", modifier = Modifier.weight(1f))
            }

            // Weekly Chart Card ("Tuần này") - Compact
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                color = Color.White
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Tuần này", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF202124))
                        Text(text = "${userProfile.weeklyCompleted}/${userProfile.weeklyTotal} việc", fontSize = 12.sp, color = SecondaryText, fontWeight = FontWeight.Medium)
                    }

                    // Bar Chart Mock (Compact)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(75.dp),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        val days = listOf("T2" to 0.5f, "T3" to 0.9f, "T4" to 0.4f, "T5" to 0.7f, "T6" to 0.6f, "T7" to 0.3f, "CN" to 0.2f)
                        days.forEach { (day, heightRatio) ->
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Bottom,
                                modifier = Modifier.fillMaxHeight()
                            ) {
                                Box(
                                    modifier = Modifier
                                        .width(16.dp)
                                        .height((50 * heightRatio).dp)
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(if (day == "T3") PrimaryBlue else Color(0xFFE5E7EB))
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(text = day, fontSize = 10.sp, color = SecondaryText)
                            }
                        }
                    }
                }
            }

            // Action Items Card (Compact)
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                color = Color.White
            ) {
                Column(modifier = Modifier.padding(vertical = 4.dp)) {
                    SettingItemRow(
                        icon = Icons.Default.Sync,
                        title = "Đồng bộ dữ liệu",
                        trailingText = "Vừa xong",
                        onClick = {}
                    )
                    HorizontalDivider(color = Color(0xFFF3F4F7), thickness = 1.dp)
                    SettingItemRow(
                        icon = Icons.Default.Lock,
                        title = "Quyền riêng tư",
                        trailingText = "",
                        onClick = {}
                    )
                }
            }

            // Logout Button (Compact)
            OutlinedButton(
                onClick = onLogout,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, Color(0xFFFEE2E2))
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.ExitToApp, contentDescription = null, tint = Color(0xFFDC2626), modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "Đăng xuất", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFFDC2626))
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun StatCard(value: String, label: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        color = Color.White
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color(0xFF202124)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = label,
                fontSize = 10.sp,
                color = SecondaryText,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

@Composable
fun SettingItemRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    trailingText: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 14.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = icon, contentDescription = null, tint = SecondaryText, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = title, fontSize = 13.sp, color = Color(0xFF202124), fontWeight = FontWeight.Medium)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (trailingText.isNotBlank()) {
                Text(text = trailingText, fontSize = 11.sp, color = SecondaryText)
                Spacer(modifier = Modifier.width(4.dp))
            }
            Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = SecondaryText, modifier = Modifier.size(16.dp))
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true, widthDp = 392, heightDp = 800)
@Composable
fun ProfileScreenPreview() {
    com.example.uth_todov2.ui.theme.UTH_todov2Theme {
        ProfileScreen(
            userProfile = UserProfile(),
            onNavigate = {},
            onLogout = {},
            onAddTaskClick = {}
        )
    }
}
