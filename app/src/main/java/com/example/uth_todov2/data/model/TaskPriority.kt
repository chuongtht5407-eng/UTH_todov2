package com.example.uth_todov2.data.model

enum class TaskPriority(val displayName: String, val weight: Int) {
    LOW("Thấp", 1),
    MEDIUM("Vừa", 2),
    HIGH("Cao", 3)
}
