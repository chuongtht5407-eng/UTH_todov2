package com.example.uth_todov2.data.model

data class Task(
    val id: String = java.util.UUID.randomUUID().toString(),
    val title: String,
    val note: String = "",
    val category: TaskCategory = TaskCategory.HOC_TAP,
    val dueDate: String = "Hôm nay",
    val dueTime: String = "09:00",
    val priority: TaskPriority = TaskPriority.MEDIUM,
    val isCompleted: Boolean = false,
    val reminderEnabled: Boolean = true,
    val reminderMinutesBefore: Int = 15,
    val subtasks: List<SubTask> = emptyList(),
    val createdAt: Long = System.currentTimeMillis()
)
