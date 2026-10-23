package com.example.uth_todov2.data.model

data class UserProfile(
    val id: String = "1",
    val name: String = "Minh Anh",
    val email: String = "minhanh@gmail.com",
    val avatarInitials: String = "MA",
    val completedTasksCount: Int = 128,
    val streakDays: Int = 12,
    val onTimeRate: Int = 86,
    val googleLinked: Boolean = true,
    val weeklyCompleted: Int = 18,
    val weeklyTotal: Int = 22
)
