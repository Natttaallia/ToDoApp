package com.example.todo.data.repositories

import com.example.todo.data.db.TaskDao
import com.example.todo.domain.models.Task
import com.example.todo.domain.repositories.TasksRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.todo.domain.models.Result
import com.example.todo.domain.models.Result.Success
import com.example.todo.domain.models.Result.Error

class TasksRepositoryImpl constructor(
    private val taskDao: TaskDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : TasksRepository {

    override suspend fun getTasks(): Result<List<Task>> = withContext(ioDispatcher) {
        return@withContext try {
            Success(taskDao.getTasks())
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun getTask(taskId: String): Result<Task> = withContext(ioDispatcher) {
        try {
            val task = taskDao.getTaskById(taskId)
            if (task != null) {
                return@withContext Success(task)
            } else {
                return@withContext Error(Exception("Task not found!"))
            }
        } catch (e: Exception) {
            return@withContext Error(e)
        }
    }

    override suspend fun saveTask(task: Task) = withContext(ioDispatcher) {
        taskDao.insertTask(task)
    }

    override suspend fun completeTask(taskId: String) {
        taskDao.updateCompleted(taskId, true)
    }

    override suspend fun activateTask(taskId: String) {
        taskDao.updateCompleted(taskId, false)
    }

    override suspend fun deleteTask(taskId: String) = withContext<Unit>(ioDispatcher) {
        taskDao.deleteTaskById(taskId)
    }
}