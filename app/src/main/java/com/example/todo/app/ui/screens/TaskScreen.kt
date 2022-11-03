package com.example.todo.app.ui.screens

import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.todo.app.viewmodels.TaskViewModel

@Composable
fun TaskScreen(
    onEditTask: (String) -> Unit,
    onBack: () -> Unit,
    onDeleteTask: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TaskViewModel = TaskViewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
}