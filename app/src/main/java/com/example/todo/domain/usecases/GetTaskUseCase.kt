package com.example.todo.domain.usecases

import com.example.todo.domain.models.Task
import com.example.todo.domain.repositories.TasksRepository
import javax.inject.Inject
import com.example.todo.domain.models.Result


class GetTaskUseCase @Inject constructor(private val repo: TasksRepository) {

    suspend fun execute(taskId: String): Result<Task> {
        return repo.getTask(taskId)
    }
}