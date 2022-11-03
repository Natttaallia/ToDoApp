package com.example.todo.app.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.R
import com.example.todo.app.navigation.ADD_RESULT_OK
import com.example.todo.app.navigation.DELETE_RESULT_OK
import com.example.todo.app.navigation.EDIT_RESULT_OK
import com.example.todo.app.data.FilteringUiInfo
import com.example.todo.app.data.Status
import com.example.todo.app.data.TasksFilterType
import com.example.todo.app.data.states.TasksUiState
import com.example.todo.app.utils.WhileUiSubscribed
import com.example.todo.domain.models.Task
import com.example.todo.domain.repositories.TasksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.todo.domain.models.Result.Success
import com.example.todo.domain.models.Result
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*


const val TASKS_FILTER_SAVED_STATE_KEY = "TASKS_FILTER_SAVED_STATE_KEY"

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val tasksRepository: TasksRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _savedFilterType =
        savedStateHandle.getStateFlow(TASKS_FILTER_SAVED_STATE_KEY, TasksFilterType.ALL_TASKS)

    private val _filterUiInfo = _savedFilterType.map { getFilterUiInfo(it) }.distinctUntilChanged()
    private val _userMessage: MutableStateFlow<Int?> = MutableStateFlow(null)
    private val _isLoading = MutableStateFlow(false)
    private val _filteredTasksAsync =
        combine(tasksRepository.getTasksStream(), _savedFilterType) { tasks, type ->
            filterTasks(tasks, type)
        }
            .map { Status.Success(it) }
            .onStart<Status<List<Task>>> { emit(Status.Loading) }

    val uiState: StateFlow<TasksUiState> = combine(
        _filterUiInfo, _isLoading, _userMessage, _filteredTasksAsync
    ) { filterUiInfo, isLoading, userMessage, tasksAsync ->
        when (tasksAsync) {
            Status.Loading -> {
                TasksUiState(isLoading = true)
            }
            is Status.Success -> {
                TasksUiState(
                    items = tasksAsync.data,
                    filteringUiInfo = filterUiInfo,
                    isLoading = isLoading,
                    userMessage = userMessage
                )
            }
        }
    }
        .stateIn(
            scope = viewModelScope,
            started = WhileUiSubscribed,
            initialValue = TasksUiState(isLoading = true)
        )

    fun setFiltering(requestType: TasksFilterType) {
        savedStateHandle[TASKS_FILTER_SAVED_STATE_KEY] = requestType
    }

    fun completeTask(task: Task, completed: Boolean) = viewModelScope.launch {
        if (completed) {
            tasksRepository.completeTask(task.id)
            showSnackbarMessage(R.string.task_marked_complete)
        } else {
            tasksRepository.activateTask(task.id)
            showSnackbarMessage(R.string.task_marked_active)
        }
    }

    fun showEditResultMessage(result: Int) {
        when (result) {
            EDIT_RESULT_OK -> showSnackbarMessage(R.string.successfully_saved_task_message)
            ADD_RESULT_OK -> showSnackbarMessage(R.string.successfully_added_task_message)
            DELETE_RESULT_OK -> showSnackbarMessage(R.string.successfully_deleted_task_message)
        }
    }

    fun snackbarMessageShown() {
        _userMessage.value = null
    }

    private fun showSnackbarMessage(message: Int) {
        _userMessage.value = message
    }

    private fun filterTasks(
        tasksResult: Result<List<Task>>,
        filteringType: TasksFilterType
    ): List<Task> = if (tasksResult is Success) {
        filterItems(tasksResult.data, filteringType)
    } else {
        showSnackbarMessage(R.string.loading_tasks_error)
        emptyList()
    }

    private fun filterItems(tasks: List<Task>, filteringType: TasksFilterType): List<Task> {
        val tasksToShow = ArrayList<Task>()
        for (task in tasks) {
            when (filteringType) {
                TasksFilterType.ALL_TASKS -> tasksToShow.add(task)
                TasksFilterType.ACTIVE_TASKS -> if (!task.isCompleted) {
                    tasksToShow.add(task)
                }
                TasksFilterType.COMPLETED_TASKS -> if (task.isCompleted) {
                    tasksToShow.add(task)
                }
            }
        }
        return tasksToShow
    }

    private fun getFilterUiInfo(requestType: TasksFilterType): FilteringUiInfo =
        when (requestType) {
            TasksFilterType.ALL_TASKS -> {
                FilteringUiInfo(
                    R.string.label_all, R.string.no_tasks_all,
                    R.drawable.logo_no_fill
                )
            }
            TasksFilterType.ACTIVE_TASKS -> {
                FilteringUiInfo(
                    R.string.label_active, R.string.no_tasks_active,
                    R.drawable.ic_check_circle_96dp
                )
            }
            TasksFilterType.COMPLETED_TASKS -> {
                FilteringUiInfo(
                    R.string.label_completed, R.string.no_tasks_completed,
                    R.drawable.ic_verified_user_96dp
                )
            }
        }

    fun refresh() {
        _isLoading.value = true
        viewModelScope.launch {
            delay(3000)
            _isLoading.value = false
        }
    }
}