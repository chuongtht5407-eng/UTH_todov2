package com.example.uth_todov2.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uth_todov2.data.model.Task
import com.example.uth_todov2.data.model.TaskCategory
import com.example.uth_todov2.ui.components.BottomNavigationBar
import com.example.uth_todov2.ui.theme.PrimaryBlue
import com.example.uth_todov2.ui.theme.SecondaryText

@Composable
fun HomeScreen(
    tasks: List<Task>,
    completedCount: Int,
    totalCount: Int,
    userName: String,
    avatarInitials: String,
    onNavigate: (String) -> Unit,
    onTaskClick: (String) -> Unit,
    onToggleTask: (String) -> Unit,
    onAddTaskClick: () -> Unit
) {
    val completionPercentage = if (totalCount > 0) (completedCount * 100) / totalCount else 0

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                currentRoute = "home",
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
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { Spacer(modifier = Modifier.height(12.dp)) }

            // Header
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(CircleShape)
                                .background(PrimaryBlue),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = avatarInitials,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Thứ Ba, 20 tháng 10",
                                fontSize = 12.sp,
                                color = SecondaryText
                            )
                            Text(
                                text = "Chào $userName!",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF202124)
                            )
                        }
                    }

                    IconButton(
                        onClick = { onNavigate("notifications") },
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Thông báo",
                            tint = Color(0xFF202124),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            // Today Progress Card
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    color = PrimaryBlue
                ) {
                    Row(
                        modifier = Modifier.padding(20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Tiến độ hôm nay",
                                fontSize = 13.sp,
                                color = Color.White.copy(alpha = 0.8f),
                                fontWeight = FontWeight.Medium
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "$completedCount/$totalCount việc",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Bắt đầu tốt với việc nhỏ nhất nhé.",
                                fontSize = 12.sp,
                                color = Color.White.copy(alpha = 0.85f)
                            )
                        }

                        // Circular progress indicator mock
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(52.dp)
                                    .clip(CircleShape)
                                    .background(PrimaryBlue),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "$completionPercentage%",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }

            // Two Category Cards
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Study
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onNavigate("taskList") },
                        shape = RoundedCornerShape(16.dp),
                        color = Color.White
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color(0xFFEEF1FF)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Book,
                                    contentDescription = null,
                                    tint = PrimaryBlue,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "Học tập",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = Color(0xFF202124)
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "${tasks.count { it.category == TaskCategory.HOC_TAP && !it.isCompleted }} việc còn lại",
                                fontSize = 11.sp,
                                color = SecondaryText
                            )
                        }
                    }

                    // Life
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onNavigate("taskList") },
                        shape = RoundedCornerShape(16.dp),
                        color = Color.White
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color(0xFFFFFAEB)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.WbSunny,
                                    contentDescription = null,
                                    tint = Color(0xFFFF9800),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "Cuộc sống",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = Color(0xFF202124)
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "${tasks.count { it.category == TaskCategory.CUOC_SONG && !it.isCompleted }} việc còn lại",
                                fontSize = 11.sp,
                                color = SecondaryText
                            )
                        }
                    }
                }
            }

            // Today Tasks Header
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Việc hôm nay",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color(0xFF202124)
                    )
                    TextButton(onClick = { onNavigate("taskList") }) {
                        Text(
                            text = "Xem tất cả",
                            fontSize = 13.sp,
                            color = PrimaryBlue,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            // Task List (limit to first 4 for home)
            items(tasks.take(4)) { task ->
                HomeTaskRow(
                    task = task,
                    onToggle = { onToggleTask(task.id) },
                    onClick = { onTaskClick(task.id) }
                )
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}

@Composable
fun HomeTaskRow(
    task: Task,
    onToggle: () -> Unit,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        color = Color.White
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Checkbox
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(if (task.isCompleted) PrimaryBlue else Color(0xFFE5E7EB))
                    .clickable { onToggle() },
                contentAlignment = Alignment.Center
            ) {
                if (task.isCompleted) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = task.title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (task.isCompleted) SecondaryText else Color(0xFF202124),
                    textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = if (task.category == TaskCategory.HOC_TAP) Color(0xFFEEF1FF) else Color(0xFFFFFAEB)
                    ) {
                        Text(text = task.category.displayName, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp), fontSize = 10.sp, color = if (task.category == TaskCategory.HOC_TAP) PrimaryBlue else Color(0xFFFF9800), fontWeight = FontWeight.Medium)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = task.dueTime, fontSize = 11.sp, color = SecondaryText)
                }
            }
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true, widthDp = 392, heightDp = 800)
@Composable
fun HomeScreenPreview() {
    val sampleTasks = listOf(
        Task(id = "1", title = "Nộp bài tập Lập trình di động", category = TaskCategory.HOC_TAP, dueTime = "09:00", isCompleted = false),
        Task(id = "2", title = "Ôn chương 3 – Cấu trúc dữ liệu", category = TaskCategory.HOC_TAP, dueTime = "14:00", isCompleted = false),
        Task(id = "3", title = "Đi chợ mua đồ cho tuần", category = TaskCategory.CUOC_SONG, dueTime = "17:30", isCompleted = false),
        Task(id = "4", title = "Tập thể dục 30 phút", category = TaskCategory.CUOC_SONG, dueTime = "06:30", isCompleted = true)
    )
    com.example.uth_todov2.ui.theme.UTH_todov2Theme {
        HomeScreen(
            tasks = sampleTasks,
            completedCount = 3,
            totalCount = 7,
            userName = "Minh Anh",
            avatarInitials = "MA",
            onNavigate = {},
            onTaskClick = {},
            onToggleTask = {},
            onAddTaskClick = {}
        )
    }
}

