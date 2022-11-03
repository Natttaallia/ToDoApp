package com.example.todo.domain.repositories

import com.example.todo.domain.models.Task
import com.example.todo.domain.models.Result
import kotlinx.coroutines.flow.Flow

interface TasksRepository {

    suspend fun getTasks(): Result<List<Task>>

    suspend fun getTask(taskId: String): Result<Task>

    suspend fun saveTask(task: Task)

    suspend fun completeTask(taskId: String)

    suspend fun activateTask(taskId: String)

    suspend fun deleteTask(taskId: String)

    fun getTasksStream(): Flow<Result<List<Task>>>

}