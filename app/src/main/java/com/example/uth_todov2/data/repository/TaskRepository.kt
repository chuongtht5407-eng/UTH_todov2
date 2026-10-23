package com.example.uth_todov2.data.repository

import com.example.uth_todov2.data.model.NotificationItem
import com.example.uth_todov2.data.model.Task
import com.example.uth_todov2.data.model.UserProfile
import kotlinx.coroutines.flow.StateFlow

interface TaskRepository {
    val tasks: StateFlow<List<Task>>
    val userProfile: StateFlow<UserProfile>
    val notifications: StateFlow<List<NotificationItem>>

    fun getTaskById(id: String): Task?
    fun addTask(task: Task)
    fun updateTask(task: Task)
    fun deleteTask(id: String)
    fun toggleTaskCompletion(id: String)
    fun toggleSubTaskCompletion(taskId: String, subTaskId: String)
    fun addSubTaskToTask(taskId: String, subTaskTitle: String)
    fun deleteSubTaskFromTask(taskId: String, subTaskId: String)
    fun updateProfile(profile: UserProfile)
    fun markNotificationAsRead(id: String)
}
