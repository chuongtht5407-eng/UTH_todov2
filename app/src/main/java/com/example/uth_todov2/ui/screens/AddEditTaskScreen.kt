package com.example.uth_todov2.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uth_todov2.data.model.Task
import com.example.uth_todov2.data.model.TaskCategory
import com.example.uth_todov2.data.model.TaskPriority
import com.example.uth_todov2.ui.theme.PrimaryBlue
import com.example.uth_todov2.ui.theme.SecondaryText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTaskScreen(
    existingTask: Task? = null,
    onSave: (Task) -> Unit,
    onCancel: () -> Unit
) {
    var title by remember { mutableStateOf(existingTask?.title ?: "") }
    var note by remember { mutableStateOf(existingTask?.note ?: "") }
    var category by remember { mutableStateOf(existingTask?.category ?: TaskCategory.HOC_TAP) }
    var dueDate by remember { mutableStateOf(existingTask?.dueDate ?: "Hôm nay, 21/10") }
    var dueTime by remember { mutableStateOf(existingTask?.dueTime ?: "19:00") }
    var reminderEnabled by remember { mutableStateOf(existingTask?.reminderEnabled ?: true) }
    var priority by remember { mutableStateOf(existingTask?.priority ?: TaskPriority.MEDIUM) }
    var showError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (existingTask == null) "Việc mới" else "Sửa công việc",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color(0xFF202124)
                    )
                },
                navigationIcon = {
                    TextButton(onClick = onCancel) {
                        Text(text = "Hủy", color = SecondaryText, fontSize = 15.sp)
                    }
                },
                actions = {
                    TextButton(
                        onClick = {
                            if (title.isBlank()) {
                                showError = true
                            } else {
                                val newTask = if (existingTask == null) {
                                    Task(
                                        title = title,
                                        note = note,
                                        category = category,
                                        dueDate = dueDate,
                                        dueTime = dueTime,
                                        reminderEnabled = reminderEnabled,
                                        priority = priority
                                    )
                                } else {
                                    existingTask.copy(
                                        title = title,
                                        note = note,
                                        category = category,
                                        dueDate = dueDate,
                                        dueTime = dueTime,
                                        reminderEnabled = reminderEnabled,
                                        priority = priority
                                    )
                                }
                                onSave(newTask)
                            }
                        }
                    ) {
                        Text(text = "Lưu", color = PrimaryBlue, fontWeight = FontWeight.Bold, fontSize = 15.sp)
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
            // Task Title Input
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(text = "Tên công việc *", fontSize = 13.sp, fontWeight = FontWeight.Medium, color = Color(0xFF202124))
                OutlinedTextField(
                    value = title,
                    onValueChange = {
                        title = it
                        if (it.isNotBlank()) showError = false
                    },
                    placeholder = { Text(text = "Ôn chương 4 - Giải thuật", color = SecondaryText) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    isError = showError,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryBlue,
                        unfocusedBorderColor = Color(0xFFE5E7EB),
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    singleLine = true
                )
                if (showError) {
                    Text(text = "Tên công việc không được để trống", fontSize = 11.sp, color = Color(0xFFEA4335))
                }
            }

            // Note Input
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(text = "Ghi chú", fontSize = 13.sp, fontWeight = FontWeight.Medium, color = Color(0xFF202124))
                OutlinedTextField(
                    value = note,
                    onValueChange = { note = it },
                    placeholder = { Text(text = "Thêm ghi chú (không bắt buộc)", color = SecondaryText) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryBlue,
                        unfocusedBorderColor = Color(0xFFE5E7EB),
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )
            }

            // Category Selection
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(text = "Nhóm", fontSize = 13.sp, fontWeight = FontWeight.Medium, color = Color(0xFF202124))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    val isStudy = category == TaskCategory.HOC_TAP
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { category = TaskCategory.HOC_TAP },
                        shape = RoundedCornerShape(12.dp),
                        color = if (isStudy) PrimaryBlue else Color.White,
                        border = if (isStudy) null else BorderStroke(1.dp, Color(0xFFE5E7EB))
                    ) {
                        Box(
                            modifier = Modifier.padding(vertical = 14.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Học tập",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = if (isStudy) Color.White else Color(0xFF202124)
                            )
                        }
                    }

                    val isLife = category == TaskCategory.CUOC_SONG
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { category = TaskCategory.CUOC_SONG },
                        shape = RoundedCornerShape(12.dp),
                        color = if (isLife) Color(0xFFFF9800) else Color.White,
                        border = if (isLife) null else BorderStroke(1.dp, Color(0xFFE5E7EB))
                    ) {
                        Box(
                            modifier = Modifier.padding(vertical = 14.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Cuộc sống",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = if (isLife) Color.White else Color(0xFF202124)
                            )
                        }
                    }
                }
            }

            // Date, Time & Reminder card
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = Color.White
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Date
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.CalendarToday, contentDescription = null, tint = SecondaryText, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(text = "Ngày", fontSize = 14.sp, color = Color(0xFF202124), fontWeight = FontWeight.Medium)
                        }
                        Text(text = dueDate, fontSize = 14.sp, color = PrimaryBlue, fontWeight = FontWeight.Bold)
                    }

                    HorizontalDivider(color = Color(0xFFF3F4F7))

                    // Time
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Schedule, contentDescription = null, tint = SecondaryText, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(text = "Giờ", fontSize = 14.sp, color = Color(0xFF202124), fontWeight = FontWeight.Medium)
                        }
                        Text(text = dueTime, fontSize = 14.sp, color = PrimaryBlue, fontWeight = FontWeight.Bold)
                    }

                    HorizontalDivider(color = Color(0xFFF3F4F7))

                    // Reminder Switch
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Notifications, contentDescription = null, tint = SecondaryText, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(text = "Nhắc tôi", fontSize = 14.sp, color = Color(0xFF202124), fontWeight = FontWeight.Medium)
                                Text(text = "Trước 15 phút", fontSize = 11.sp, color = SecondaryText)
                            }
                        }
                        Switch(
                            checked = reminderEnabled,
                            onCheckedChange = { reminderEnabled = it },
                            colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = PrimaryBlue)
                        )
                    }
                }
            }

            // Priority Segmented Control
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(text = "Mức ưu tiên", fontSize = 13.sp, fontWeight = FontWeight.Medium, color = Color(0xFF202124))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFE5E7EB))
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    listOf(TaskPriority.LOW to "Thấp", TaskPriority.MEDIUM to "Vừa", TaskPriority.HIGH to "Cao").forEach { (p, label) ->
                        val selected = priority == p
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (selected) Color.White else Color.Transparent)
                                .clickable { priority = p }
                                .padding(vertical = 10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = label,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (selected) PrimaryBlue else SecondaryText
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Large Save Button
            Button(
                onClick = {
                    if (title.isBlank()) {
                        showError = true
                    } else {
                        val newTask = if (existingTask == null) {
                            Task(
                                title = title,
                                note = note,
                                category = category,
                                dueDate = dueDate,
                                dueTime = dueTime,
                                reminderEnabled = reminderEnabled,
                                priority = priority
                            )
                        } else {
                            existingTask.copy(
                                title = title,
                                note = note,
                                category = category,
                                dueDate = dueDate,
                                dueTime = dueTime,
                                reminderEnabled = reminderEnabled,
                                priority = priority
                            )
                        }
                        onSave(newTask)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
            ) {
                Text(
                    text = "Lưu công việc",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true, widthDp = 392, heightDp = 800)
@Composable
fun AddEditTaskScreenPreview() {
    com.example.uth_todov2.ui.theme.UTH_todov2Theme {
        AddEditTaskScreen(
            existingTask = null,
            onSave = {},
            onCancel = {}
        )
    }
}

