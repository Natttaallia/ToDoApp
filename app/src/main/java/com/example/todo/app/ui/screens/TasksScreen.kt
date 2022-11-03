package com.example.todo.app.ui.screens

import androidx.annotation.StringRes
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.todo.app.viewmodels.TasksViewModel
import com.example.todo.domain.models.Task

@Composable
fun TasksScreen(
    @StringRes userMessage: Int,
    onAddTask: () -> Unit,
    onTaskClick: (Task) -> Unit,
    onUserMessageDisplayed: () -> Unit,
    openDrawer: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TasksViewModel = TasksViewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {

}