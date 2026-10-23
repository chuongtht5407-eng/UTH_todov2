package com.example.uth_todov2.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
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
import com.example.uth_todov2.data.model.SubTask
import com.example.uth_todov2.data.model.TaskCategory
import com.example.uth_todov2.data.model.TaskPriority
import com.example.uth_todov2.ui.theme.DangerRed
import com.example.uth_todov2.ui.theme.PrimaryBlue
import com.example.uth_todov2.ui.theme.SecondaryText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(
    task: Task?,
    onNavigateBack: () -> Unit,
    onEdit: (String) -> Unit,
    onToggleTask: (String) -> Unit,
    onDeleteTask: (String) -> Unit,
    onToggleSubTask: (String, String) -> Unit,
    onAddSubTask: (String, String) -> Unit,
    onDeleteSubTask: (String, String) -> Unit
) {
    if (task == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Không tìm thấy công việc", color = SecondaryText)
        }
        return
    }

    var newSubTaskTitle by remember { mutableStateOf("") }
    var showDeleteDialog by remember { mutableStateOf(false) }

    val completedSubtasks = task.subtasks.count { it.isCompleted }
    val totalSubtasks = task.subtasks.size
    val subtaskProgress = if (totalSubtasks > 0) completedSubtasks.toFloat() / totalSubtasks.toFloat() else 0f

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text(text = "Xóa công việc") },
            text = { Text(text = "Bạn có chắc chắn muốn xóa công việc \"${task.title}\" không?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        onDeleteTask(task.id)
                    }
                ) {
                    Text(text = "Xóa", color = DangerRed, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text(text = "Hủy", color = SecondaryText)
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    TextButton(onClick = onNavigateBack) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null, tint = PrimaryBlue, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "Danh sách", color = PrimaryBlue, fontSize = 15.sp, fontWeight = FontWeight.Medium)
                        }
                    }
                },
                actions = {
                    TextButton(onClick = { onEdit(task.id) }) {
                        Text(text = "Sửa", color = PrimaryBlue, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color(0xFFF9FAFB)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Chips row
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = if (task.category == TaskCategory.HOC_TAP) Color(0xFFEEF1FF) else Color(0xFFFFFAEB)
                ) {
                    Text(
                        text = task.category.displayName,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        fontSize = 11.sp,
                        color = if (task.category == TaskCategory.HOC_TAP) PrimaryBlue else Color(0xFFFF9800),
                        fontWeight = FontWeight.Bold
                    )
                }

                if (task.priority == TaskPriority.HIGH) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = Color(0xFFFEE2E2)
                    ) {
                        Text(
                            text = "Ưu tiên cao",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            fontSize = 11.sp,
                            color = Color(0xFFDC2626),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Title
            Text(
                text = task.title,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF202124),
                textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None
            )

            // Info Card
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = Color.White
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    DetailInfoRow(label = "Hạn chót", value = "${task.dueDate}, ${task.dueTime}")
                    DetailInfoRow(label = "Nhắc nhở", value = if (task.reminderEnabled) "Trước ${task.reminderMinutesBefore} phút" else "Không")
                    DetailInfoRow(label = "Lặp lại", value = "Không")
                }
            }

            // Note section
            if (task.note.isNotBlank()) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = "Ghi chú", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF202124))
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        color = Color.White
                    ) {
                        Text(
                            text = task.note,
                            modifier = Modifier.padding(16.dp),
                            fontSize = 13.sp,
                            color = Color(0xFF202124)
                        )
                    }
                }
            }

            // Subtasks / Checklist section
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Việc nhỏ", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF202124))
                    if (totalSubtasks > 0) {
                        Text(text = "$completedSubtasks/$totalSubtasks", fontSize = 12.sp, color = SecondaryText, fontWeight = FontWeight.Medium)
                    }
                }

                if (totalSubtasks > 0) {
                    LinearProgressIndicator(
                        progress = { subtaskProgress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        color = PrimaryBlue,
                        trackColor = Color(0xFFE5E7EB)
                    )
                }

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        if (task.subtasks.isEmpty()) {
                            Text(text = "Chưa có việc nhỏ nào", fontSize = 13.sp, color = SecondaryText, modifier = Modifier.padding(8.dp))
                        } else {
                            task.subtasks.forEach { sub ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .weight(1f)
                                            .clickable { onToggleSubTask(task.id, sub.id) }
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(20.dp)
                                                .clip(RoundedCornerShape(4.dp))
                                                .background(if (sub.isCompleted) PrimaryBlue else Color(0xFFE5E7EB)),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            if (sub.isCompleted) {
                                                Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(12.dp))
                                            }
                                        }
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Text(
                                            text = sub.title,
                                            fontSize = 13.sp,
                                            color = if (sub.isCompleted) SecondaryText else Color(0xFF202124),
                                            textDecoration = if (sub.isCompleted) TextDecoration.LineThrough else TextDecoration.None
                                        )
                                    }

                                    IconButton(
                                        onClick = { onDeleteSubTask(task.id, sub.id) },
                                        modifier = Modifier.size(28.dp)
                                    ) {
                                        Icon(imageVector = Icons.Default.Delete, contentDescription = null, tint = DangerRed, modifier = Modifier.size(16.dp))
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Add subtask input row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(
                                value = newSubTaskTitle,
                                onValueChange = { newSubTaskTitle = it },
                                placeholder = { Text(text = "Thêm việc nhỏ", fontSize = 13.sp, color = SecondaryText) },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp),
                                shape = RoundedCornerShape(10.dp),
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = PrimaryBlue,
                                    unfocusedBorderColor = Color(0xFFE5E7EB)
                                )
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(
                                onClick = {
                                    if (newSubTaskTitle.isNotBlank()) {
                                        onAddSubTask(task.id, newSubTaskTitle)
                                        newSubTaskTitle = ""
                                    }
                                },
                                modifier = Modifier.height(48.dp),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
                            ) {
                                Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = Color.White)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Action Buttons
            Button(
                onClick = { onToggleTask(task.id) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
            ) {
                Text(
                    text = if (task.isCompleted) "Đánh dấu chưa hoàn thành" else "Đánh dấu hoàn thành",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            TextButton(
                onClick = { showDeleteDialog = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Xóa công việc",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = DangerRed
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun DetailInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, fontSize = 14.sp, color = SecondaryText)
        Text(text = value, fontSize = 14.sp, color = Color(0xFF202124), fontWeight = FontWeight.Medium)
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true, widthDp = 392, heightDp = 800)
@Composable
fun TaskDetailScreenPreview() {
    val sampleTask = Task(
        id = "1",
        title = "Nộp bài tập Lập trình di động",
        note = "Nộp file .zip lên hệ thống của trường. Nhớ kèm ảnh chụp màn hình app chạy trên máy ảo.",
        category = TaskCategory.HOC_TAP,
        dueDate = "Hôm nay",
        dueTime = "09:00",
        priority = TaskPriority.HIGH,
        subtasks = listOf(
            SubTask(title = "Hoàn thiện màn hình đăng nhập", isCompleted = true),
            SubTask(title = "Chụp ảnh màn hình app", isCompleted = false)
        )
    )
    com.example.uth_todov2.ui.theme.UTH_todov2Theme {
        TaskDetailScreen(
            task = sampleTask,
            onNavigateBack = {},
            onEdit = {},
            onToggleTask = {},
            onDeleteTask = {},
            onToggleSubTask = { _, _ -> },
            onAddSubTask = { _, _ -> },
            onDeleteSubTask = { _, _ -> }
        )
    }
}

