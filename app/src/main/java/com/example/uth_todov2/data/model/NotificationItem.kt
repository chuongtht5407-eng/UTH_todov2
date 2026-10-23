package com.example.uth_todov2.data.model

data class NotificationItem(
    val id: String = java.util.UUID.randomUUID().toString(),
    val title: String,
    val message: String,
    val timeAgo: String,
    val isRead: Boolean = false
)
