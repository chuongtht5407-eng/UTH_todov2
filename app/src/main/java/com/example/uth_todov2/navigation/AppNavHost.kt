package com.example.uth_todov2.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.uth_todov2.ui.screens.*
import com.example.uth_todov2.viewmodel.TodoViewModel

@Composable
fun AppNavHost(viewModel: TodoViewModel = viewModel()) {
    val navController = rememberNavController()

    val tasks by viewModel.tasks.collectAsState()
    val userProfile by viewModel.userProfile.collectAsState()
    val notifications by viewModel.notifications.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedCategory by viewModel.selectedCategoryFilter.collectAsState()
    val filteredTasks by viewModel.filteredTasks.collectAsState()
    val completedCount by viewModel.todayCompletedCount.collectAsState()
    val totalCount by viewModel.todayTotalCount.collectAsState()

    NavHost(
        navController = navController,
        startDestination = AppRoutes.SPLASH
    ) {
        // 1. Splash
        composable(AppRoutes.SPLASH) {
            SplashScreen(
                onNavigateToOnboarding = {
                    navController.navigate(AppRoutes.ONBOARDING) {
                        popUpTo(AppRoutes.SPLASH) { inclusive = true }
                    }
                }
            )
        }

        // 2, 3, 4. Onboarding
        composable(AppRoutes.ONBOARDING) {
            OnboardingScreen(
                onFinish = {
                    navController.navigate(AppRoutes.WELCOME) {
                        popUpTo(AppRoutes.ONBOARDING) { inclusive = true }
                    }
                },
                onSkip = {
                    navController.navigate(AppRoutes.WELCOME) {
                        popUpTo(AppRoutes.ONBOARDING) { inclusive = true }
                    }
                }
            )
        }

        // 9. Welcome / Login
        composable(AppRoutes.WELCOME) {
            WelcomeLoginScreen(
                onNavigateBack = { navController.popBackStack() },
                onContinueWithoutAccount = {
                    navController.navigate(AppRoutes.HOME) {
                        popUpTo(AppRoutes.WELCOME) { inclusive = true }
                    }
                },
                onGoogleLogin = {
                    // Simulate google login success -> navigate home
                    navController.navigate(AppRoutes.HOME) {
                        popUpTo(AppRoutes.WELCOME) { inclusive = true }
                    }
                }
            )
        }

        // 5. Home
        composable(AppRoutes.HOME) {
            HomeScreen(
                tasks = tasks,
                completedCount = completedCount,
                totalCount = totalCount,
                userName = userProfile.name,
                avatarInitials = userProfile.avatarInitials,
                onNavigate = { route ->
                    if (route != "home") {
                        navController.navigate(route) {
                            popUpTo(AppRoutes.HOME) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                onTaskClick = { taskId ->
                    navController.navigate(AppRoutes.taskDetailRoute(taskId))
                },
                onToggleTask = { taskId ->
                    viewModel.toggleTaskCompletion(taskId)
                },
                onAddTaskClick = {
                    navController.navigate(AppRoutes.addEditTaskRoute())
                }
            )
        }

        // 6. Task List
        composable(AppRoutes.TASK_LIST) {
            TaskListScreen(
                tasks = filteredTasks,
                searchQuery = searchQuery,
                selectedCategory = selectedCategory,
                onSearchQueryChanged = { viewModel.setSearchQuery(it) },
                onCategorySelected = { viewModel.setCategoryFilter(it) },
                onNavigate = { route ->
                    if (route != "taskList") {
                        navController.navigate(route) {
                            popUpTo(AppRoutes.HOME) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                onTaskClick = { taskId ->
                    navController.navigate(AppRoutes.taskDetailRoute(taskId))
                },
                onToggleTask = { taskId ->
                    viewModel.toggleTaskCompletion(taskId)
                },
                onAddTaskClick = {
                    navController.navigate(AppRoutes.addEditTaskRoute())
                }
            )
        }

        // 7. Add / Edit Task
        composable(
            route = "${AppRoutes.ADD_EDIT_TASK}?taskId={taskId}",
            arguments = listOf(navArgument("taskId") {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId")
            val existingTask = taskId?.let { viewModel.getTaskById(it) }

            AddEditTaskScreen(
                existingTask = existingTask,
                onSave = { task ->
                    if (existingTask == null) {
                        viewModel.addTask(task)
                    } else {
                        viewModel.updateTask(task)
                    }
                    navController.popBackStack()
                },
                onCancel = { navController.popBackStack() }
            )
        }

        // 8. Task Detail
        composable(
            route = "${AppRoutes.TASK_DETAIL}/{taskId}",
            arguments = listOf(navArgument("taskId") { type = NavType.StringType })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId")
            val task = taskId?.let { viewModel.getTaskById(it) }

            TaskDetailScreen(
                task = task,
                onNavigateBack = { navController.popBackStack() },
                onEdit = { id ->
                    navController.navigate(AppRoutes.addEditTaskRoute(id))
                },
                onToggleTask = { id ->
                    viewModel.toggleTaskCompletion(id)
                },
                onDeleteTask = { id ->
                    viewModel.deleteTask(id)
                    navController.popBackStack()
                },
                onToggleSubTask = { tId, sId ->
                    viewModel.toggleSubTaskCompletion(tId, sId)
                },
                onAddSubTask = { tId, title ->
                    viewModel.addSubTask(tId, title)
                },
                onDeleteSubTask = { tId, sId ->
                    viewModel.deleteSubTask(tId, sId)
                }
            )
        }

        // 11. Notifications
        composable(AppRoutes.NOTIFICATIONS) {
            NotificationsScreen(
                notifications = notifications,
                onMarkRead = { id -> viewModel.markNotificationRead(id) },
                onNavigate = { route ->
                    if (route != "notifications") {
                        navController.navigate(route) {
                            popUpTo(AppRoutes.HOME) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                onAddTaskClick = {
                    navController.navigate(AppRoutes.addEditTaskRoute())
                }
            )
        }

        // 10. Profile / Settings
        composable(AppRoutes.PROFILE) {
            SettingsScreen(
                isDarkMode = false,
                onToggleDarkMode = { _ -> },
                onNavigate = { route ->
                    if (route != "profile") {
                        navController.navigate(route) {
                            popUpTo(AppRoutes.HOME) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                onAddTaskClick = {
                    navController.navigate(AppRoutes.addEditTaskRoute())
                }
            )
        }
    }
}
