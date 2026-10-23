package com.example.uth_todov2.navigation

object AppRoutes {
    const val SPLASH = "splash"
    const val ONBOARDING = "onboarding"
    const val WELCOME = "welcome"
    const val HOME = "home"
    const val TASK_LIST = "taskList"
    const val ADD_EDIT_TASK = "addEditTask"
    const val TASK_DETAIL = "taskDetail"
    const val NOTIFICATIONS = "notifications"
    const val PROFILE = "profile"
    const val SETTINGS = "settings"

    fun taskDetailRoute(taskId: String) = "$TASK_DETAIL/$taskId"
    fun addEditTaskRoute(taskId: String? = null) = if (taskId != null) "$ADD_EDIT_TASK?taskId=$taskId" else ADD_EDIT_TASK
}
