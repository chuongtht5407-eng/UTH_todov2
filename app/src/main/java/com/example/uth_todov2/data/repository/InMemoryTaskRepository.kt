package com.example.uth_todov2.data.repository

import com.example.uth_todov2.data.model.NotificationItem
import com.example.uth_todov2.data.model.SubTask
import com.example.uth_todov2.data.model.Task
import com.example.uth_todov2.data.model.TaskCategory
import com.example.uth_todov2.data.model.TaskPriority
import com.example.uth_todov2.data.model.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class InMemoryTaskRepository : TaskRepository {

    private val initialTasks = listOf(
        Task(
            id = "1",
            title = "Nộp bài tập Lập trình di động",
            note = "Nộp file .zip lên hệ thống của trường. Nhớ kèm ảnh chụp màn hình app chạy trên máy ảo.",
            category = TaskCategory.HOC_TAP,
            dueDate = "Hôm nay",
            dueTime = "09:00",
            priority = TaskPriority.HIGH,
            isCompleted = false,
            reminderEnabled = true,
            reminderMinutesBefore = 15,
            subtasks = listOf(
                SubTask(id = "s1", title = "Hoàn thiện màn hình đăng nhập", isCompleted = true),
                SubTask(id = "s2", title = "Chụp ảnh màn hình app", isCompleted = true),
                SubTask(id = "s3", title = "Nén file và nộp bài", isCompleted = false)
            )
        ),
        Task(
            id = "2",
            title = "Ôn chương 3 – Cấu trúc dữ liệu",
            note = "Ôn lại các thuật toán cây nhị phân và đồ thị.",
            category = TaskCategory.HOC_TAP,
            dueDate = "Hôm nay",
            dueTime = "14:00",
            priority = TaskPriority.MEDIUM,
            isCompleted = false,
            reminderEnabled = true
        ),
        Task(
            id = "3",
            title = "Đi chợ mua đồ cho tuần",
            note = "Mua rau củ, thịt gà và trái cây.",
            category = TaskCategory.CUOC_SONG,
            dueDate = "Hôm nay",
            dueTime = "17:30",
            priority = TaskPriority.LOW,
            isCompleted = false,
            reminderEnabled = false
        ),
        Task(
            id = "4",
            title = "Họp nhóm đồ án",
            note = "Thảo luận tiến độ phần backend với các thành viên.",
            category = TaskCategory.HOC_TAP,
            dueDate = "Thứ Năm",
            dueTime = "20:00",
            priority = TaskPriority.HIGH,
            isCompleted = false,
            reminderEnabled = true
        ),
        Task(
            id = "5",
            title = "Tập thể dục 30 phút",
            note = "Chạy bộ quanh công viên.",
            category = TaskCategory.CUOC_SONG,
            dueDate = "Hôm nay",
            dueTime = "06:30",
            priority = TaskPriority.LOW,
            isCompleted = true,
            reminderEnabled = false
        ),
        Task(
            id = "6",
            title = "Đọc 20 trang sách",
            note = "Đọc sách Clean Code.",
            category = TaskCategory.HOC_TAP,
            dueDate = "Hôm nay",
            dueTime = "21:00",
            priority = TaskPriority.MEDIUM,
            isCompleted = true,
            reminderEnabled = false
        ),
        Task(
            id = "7",
            title = "Ôn chương 4 - Giải thuật",
            note = "Thuật toán sắp xếp và tìm kiếm nâng cao.",
            category = TaskCategory.HOC_TAP,
            dueDate = "Thứ Tư",
            dueTime = "19:00",
            priority = TaskPriority.HIGH,
            isCompleted = false,
            reminderEnabled = true
        )
    )

    private val initialNotifications = listOf(
        NotificationItem(
            id = "n1",
            title = "Nhắc nhở công việc",
            message = "Sắp đến hạn: Nộp bài tập Lập trình di động lúc 09:00",
            timeAgo = "10 phút trước",
            isRead = false
        ),
        NotificationItem(
            id = "n2",
            title = "Hoàn thành mục tiêu",
            message = "Bạn đã hoàn thành chuỗi 12 ngày liên tiếp!",
            timeAgo = "Hôm qua",
            isRead = true
        ),
        NotificationItem(
            id = "n3",
            title = "Lịch học mới",
            message = "Giảng viên đã cập nhật tài liệu môn Cấu trúc dữ liệu.",
            timeAgo = "2 ngày trước",
            isRead = true
        )
    )

    private val _tasks = MutableStateFlow(initialTasks)
    override val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    private val _userProfile = MutableStateFlow(UserProfile())
    override val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()

    private val _notifications = MutableStateFlow(initialNotifications)
    override val notifications: StateFlow<List<NotificationItem>> = _notifications.asStateFlow()

    override fun getTaskById(id: String): Task? {
        return _tasks.value.find { it.id == id }
    }

    override fun addTask(task: Task) {
        _tasks.update { current -> listOf(task) + current }
    }

    override fun updateTask(task: Task) {
        _tasks.update { current ->
            current.map { if (it.id == task.id) task else it }
        }
    }

    override fun deleteTask(id: String) {
        _tasks.update { current -> current.filter { it.id != id } }
    }

    override fun toggleTaskCompletion(id: String) {
        _tasks.update { current ->
            current.map { task ->
                if (task.id == id) task.copy(isCompleted = !task.isCompleted) else task
            }
        }
    }

    override fun toggleSubTaskCompletion(taskId: String, subTaskId: String) {
        _tasks.update { current ->
            current.map { task ->
                if (task.id == taskId) {
                    val updatedSubtasks = task.subtasks.map { sub ->
                        if (sub.id == subTaskId) sub.copy(isCompleted = !sub.isCompleted) else sub
                    }
                    task.copy(subtasks = updatedSubtasks)
                } else {
                    task
                }
            }
        }
    }

    override fun addSubTaskToTask(taskId: String, subTaskTitle: String) {
        if (subTaskTitle.isBlank()) return
        _tasks.update { current ->
            current.map { task ->
                if (task.id == taskId) {
                    val newSub = SubTask(title = subTaskTitle)
                    task.copy(subtasks = task.subtasks + newSub)
                } else {
                    task
                }
            }
        }
    }

    override fun deleteSubTaskFromTask(taskId: String, subTaskId: String) {
        _tasks.update { current ->
            current.map { task ->
                if (task.id == taskId) {
                    task.copy(subtasks = task.subtasks.filter { it.id != subTaskId })
                } else {
                    task
                }
            }
        }
    }

    override fun updateProfile(profile: UserProfile) {
        _userProfile.value = profile
    }

    override fun markNotificationAsRead(id: String) {
        _notifications.update { list ->
            list.map { if (it.id == id) it.copy(isRead = true) else it }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: InMemoryTaskRepository? = null

        fun getInstance(): InMemoryTaskRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: InMemoryTaskRepository().also { INSTANCE = it }
            }
        }
    }
}
