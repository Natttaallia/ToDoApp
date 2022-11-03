package com.example.todo.app.data.states

import com.example.todo.app.data.FilteringUiInfo
import com.example.todo.domain.models.Task

data class TasksUiState(
    val items: List<Task> = emptyList(),
    val isLoading: Boolean = false,
    val filteringUiInfo: FilteringUiInfo = FilteringUiInfo(),
    val userMessage: Int? = null
)