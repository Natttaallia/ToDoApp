package com.example.todo.domain.usecases

import com.example.todo.domain.repositories.TasksRepository
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(private val repo: TasksRepository) {

    suspend fun execute(taskId: String) {
        return repo.deleteTask(taskId)
    }
}