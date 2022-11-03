package com.example.todo.app.ui.screens

import androidx.annotation.StringRes
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.todo.app.viewmodels.AddEditTaskViewModel

@Composable
fun AddEditTaskScreen(
    @StringRes topBarTitle: Int,
    onTaskUpdate: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    viewModel: AddEditTaskViewModel = AddEditTaskViewModel()
) {

}