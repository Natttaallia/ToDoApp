package com.example.todo.app.di

import com.example.todo.data.db.ToDoDatabase
import com.example.todo.data.repositories.TasksRepositoryImpl
import com.example.todo.domain.repositories.TasksRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Singleton
    @Provides
    fun provideTasksRepository(
        database: ToDoDatabase
    ): TasksRepository {
        return TasksRepositoryImpl(database.taskDao())
    }
}