package com.example.uth_todov2.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uth_todov2.data.model.NotificationItem
import com.example.uth_todov2.ui.components.BottomNavigationBar
import com.example.uth_todov2.ui.theme.PrimaryBlue
import com.example.uth_todov2.ui.theme.SecondaryText

@Composable
fun NotificationsScreen(
    notifications: List<NotificationItem>,
    onMarkRead: (String) -> Unit,
    onNavigate: (String) -> Unit,
    onAddTaskClick: () -> Unit
) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                currentRoute = "notifications",
                onNavigate = onNavigate,
                onAddTaskClick = onAddTaskClick
            )
        },
        containerColor = Color(0xFFF9FAFB)
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item { Spacer(modifier = Modifier.height(12.dp)) }

            item {
                Text(
                    text = "Thông báo",
                    fontWeight = FontWeight.Bold,
                    fontSize = 26.sp,
                    color = Color(0xFF202124)
                )
            }

            if (notifications.isEmpty()) {
                item {
                    Box(modifier = Modifier.fillMaxWidth().padding(40.dp), contentAlignment = Alignment.Center) {
                        Text(text = "Không có thông báo nào", color = SecondaryText)
                    }
                }
            } else {
                items(notifications) { item ->
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onMarkRead(item.id) },
                        shape = RoundedCornerShape(14.dp),
                        color = if (item.isRead) Color.White else Color(0xFFEEF1FF)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(PrimaryBlue.copy(alpha = 0.1f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Notifications,
                                    contentDescription = null,
                                    tint = PrimaryBlue,
                                    modifier = Modifier.size(20.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(14.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = item.title,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = Color(0xFF202124)
                                    )
                                    Text(
                                        text = item.timeAgo,
                                        fontSize = 11.sp,
                                        color = SecondaryText
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = item.message,
                                    fontSize = 13.sp,
                                    color = SecondaryText
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true, widthDp = 392, heightDp = 800)
@Composable
fun NotificationsScreenPreview() {
    val sampleNotifications = listOf(
        NotificationItem(title = "Nhắc nhở công việc", message = "Sắp đến hạn: Nộp bài tập Lập trình di động", timeAgo = "10 phút trước", isRead = false),
        NotificationItem(title = "Hoàn thành mục tiêu", message = "Bạn đã hoàn thành chuỗi 12 ngày liên tiếp!", timeAgo = "Hôm qua", isRead = true)
    )
    com.example.uth_todov2.ui.theme.UTH_todov2Theme {
        NotificationsScreen(
            notifications = sampleNotifications,
            onMarkRead = {},
            onNavigate = {},
            onAddTaskClick = {}
        )
    }
}

