package com.example.todo.domain.usecases

import com.example.todo.domain.models.Task
import com.example.todo.domain.repositories.TasksRepository
import javax.inject.Inject

class SaveTaskUseCase @Inject constructor(private val repo: TasksRepository) {

    suspend fun execute(task: Task) {
        return repo.saveTask(task)
    }
}