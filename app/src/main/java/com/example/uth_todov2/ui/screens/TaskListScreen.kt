package com.example.uth_todov2.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Search
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
import com.example.uth_todov2.data.model.TaskPriority
import com.example.uth_todov2.ui.components.BottomNavigationBar
import com.example.uth_todov2.ui.theme.PrimaryBlue
import com.example.uth_todov2.ui.theme.SecondaryText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    tasks: List<Task>,
    searchQuery: String,
    selectedCategory: TaskCategory?,
    onSearchQueryChanged: (String) -> Unit,
    onCategorySelected: (TaskCategory?) -> Unit,
    onNavigate: (String) -> Unit,
    onTaskClick: (String) -> Unit,
    onToggleTask: (String) -> Unit,
    onAddTaskClick: () -> Unit
) {
    val pendingTasks = tasks.filter { !it.isCompleted }
    val completedTasks = tasks.filter { it.isCompleted }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                currentRoute = "taskList",
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
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item { Spacer(modifier = Modifier.height(12.dp)) }

            // Header
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Danh sách",
                        fontWeight = FontWeight.Bold,
                        fontSize = 26.sp,
                        color = Color(0xFF202124)
                    )
                    Text(
                        text = "${pendingTasks.size} việc chưa xong",
                        fontSize = 13.sp,
                        color = SecondaryText,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Search Bar
            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onSearchQueryChanged,
                    placeholder = { Text(text = "Tìm công việc", color = SecondaryText, fontSize = 14.sp) },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = SecondaryText, modifier = Modifier.size(20.dp))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryBlue,
                        unfocusedBorderColor = Color(0xFFE5E7EB),
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    singleLine = true
                )
            }

            // Category Chips Row
            item {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(
                        selected = selectedCategory == null,
                        onClick = { onCategorySelected(null) },
                        label = { Text(text = "Tất cả") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = PrimaryBlue,
                            selectedLabelColor = Color.White,
                            containerColor = Color.White,
                            labelColor = Color(0xFF202124)
                        ),
                        shape = RoundedCornerShape(10.dp)
                    )
                    FilterChip(
                        selected = selectedCategory == TaskCategory.HOC_TAP,
                        onClick = { onCategorySelected(TaskCategory.HOC_TAP) },
                        label = { Text(text = "Học tập") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = PrimaryBlue,
                            selectedLabelColor = Color.White,
                            containerColor = Color.White,
                            labelColor = Color(0xFF202124)
                        ),
                        shape = RoundedCornerShape(10.dp)
                    )
                    FilterChip(
                        selected = selectedCategory == TaskCategory.CUOC_SONG,
                        onClick = { onCategorySelected(TaskCategory.CUOC_SONG) },
                        label = { Text(text = "Cuộc sống") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = PrimaryBlue,
                            selectedLabelColor = Color.White,
                            containerColor = Color.White,
                            labelColor = Color(0xFF202124)
                        ),
                        shape = RoundedCornerShape(10.dp)
                    )
                }
            }

            // Section: CẦN LÀM
            if (pendingTasks.isNotEmpty()) {
                item {
                    Text(
                        text = "CẦN LÀM",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = SecondaryText,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
                items(pendingTasks) { task ->
                    TaskCardItem(
                        task = task,
                        onToggle = { onToggleTask(task.id) },
                        onClick = { onTaskClick(task.id) }
                    )
                }
            }

            // Section: ĐÃ XONG
            if (completedTasks.isNotEmpty()) {
                item {
                    Text(
                        text = "ĐÃ XONG",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = SecondaryText,
                        modifier = Modifier.padding(top = 12.dp)
                    )
                }
                items(completedTasks) { task ->
                    TaskCardItem(
                        task = task,
                        onToggle = { onToggleTask(task.id) },
                        onClick = { onTaskClick(task.id) }
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}

@Composable
fun TaskCardItem(
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
                        Text(
                            text = task.category.displayName,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            fontSize = 10.sp,
                            color = if (task.category == TaskCategory.HOC_TAP) PrimaryBlue else Color(0xFFFF9800),
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${task.dueDate}, ${task.dueTime}",
                        fontSize = 11.sp,
                        color = SecondaryText
                    )
                }
            }

            if (task.priority == TaskPriority.HIGH && !task.isCompleted) {
                Spacer(modifier = Modifier.width(8.dp))
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = Color(0xFFFEE2E2)
                ) {
                    Text(
                        text = "Gấp",
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        fontSize = 10.sp,
                        color = Color(0xFFDC2626),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true, widthDp = 392, heightDp = 800)
@Composable
fun TaskListScreenPreview() {
    val sampleTasks = listOf(
        Task(id = "1", title = "Nộp bài tập Lập trình di động", category = TaskCategory.HOC_TAP, priority = TaskPriority.HIGH, isCompleted = false),
        Task(id = "2", title = "Ôn chương 3 – Cấu trúc dữ liệu", category = TaskCategory.HOC_TAP, priority = TaskPriority.MEDIUM, isCompleted = false),
        Task(id = "3", title = "Đi chợ mua đồ cho tuần", category = TaskCategory.CUOC_SONG, priority = TaskPriority.LOW, isCompleted = false),
        Task(id = "4", title = "Tập thể dục 30 phút", category = TaskCategory.CUOC_SONG, priority = TaskPriority.LOW, isCompleted = true)
    )
    com.example.uth_todov2.ui.theme.UTH_todov2Theme {
        TaskListScreen(
            tasks = sampleTasks,
            searchQuery = "",
            selectedCategory = null,
            onSearchQueryChanged = {},
            onCategorySelected = {},
            onNavigate = {},
            onTaskClick = {},
            onToggleTask = {},
            onAddTaskClick = {}
        )
    }
}

