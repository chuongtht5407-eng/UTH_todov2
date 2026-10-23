package com.example.uth_todov2.viewmodel

import androidx.lifecycle.ViewModel
import com.example.uth_todov2.data.model.NotificationItem
import com.example.uth_todov2.data.model.Task
import com.example.uth_todov2.data.model.TaskCategory
import com.example.uth_todov2.data.model.UserProfile
import com.example.uth_todov2.data.repository.InMemoryTaskRepository
import com.example.uth_todov2.data.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class TodoViewModel(
    private val repository: TaskRepository = InMemoryTaskRepository.getInstance()
) : ViewModel() {

    private val viewModelScope = CoroutineScope(Dispatchers.Main)

    val tasks: StateFlow<List<Task>> = repository.tasks
    val userProfile: StateFlow<UserProfile> = repository.userProfile
    val notifications: StateFlow<List<NotificationItem>> = repository.notifications

    // Search and filter state for TaskListScreen
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _selectedCategoryFilter = MutableStateFlow<TaskCategory?>(null)
    val selectedCategoryFilter: StateFlow<TaskCategory?> = _selectedCategoryFilter

    val filteredTasks: StateFlow<List<Task>> = combine(
        tasks,
        _searchQuery,
        _selectedCategoryFilter
    ) { allTasks, query, category ->
        allTasks.filter { task ->
            val matchesQuery = query.isBlank() || task.title.contains(query, ignoreCase = true) || task.note.contains(query, ignoreCase = true)
            val matchesCategory = category == null || task.category == category
            matchesQuery && matchesCategory
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Today progress calculation
    val todayCompletedCount: StateFlow<Int> = tasks.combine(_tasksTodayDummyFlow) { taskList, _ ->
        taskList.count { it.isCompleted }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 3)

    val todayTotalCount: StateFlow<Int> = tasks.combine(_tasksTodayDummyFlow) { taskList, _ ->
        taskList.size
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 7)

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setCategoryFilter(category: TaskCategory?) {
        _selectedCategoryFilter.value = category
    }

    fun getTaskById(id: String): Task? {
        return repository.getTaskById(id)
    }

    fun addTask(task: Task) {
        repository.addTask(task)
    }

    fun updateTask(task: Task) {
        repository.updateTask(task)
    }

    fun deleteTask(id: String) {
        repository.deleteTask(id)
    }

    fun toggleTaskCompletion(id: String) {
        repository.toggleTaskCompletion(id)
    }

    fun toggleSubTaskCompletion(taskId: String, subTaskId: String) {
        repository.toggleSubTaskCompletion(taskId, subTaskId)
    }

    fun addSubTask(taskId: String, title: String) {
        repository.addSubTaskToTask(taskId, title)
    }

    fun deleteSubTask(taskId: String, subTaskId: String) {
        repository.deleteSubTaskFromTask(taskId, subTaskId)
    }

    fun updateProfile(profile: UserProfile) {
        repository.updateProfile(profile)
    }

    fun markNotificationRead(id: String) {
        repository.markNotificationAsRead(id)
    }

    companion object {
        private val _tasksTodayDummyFlow = MutableStateFlow(Unit)
    }
}
