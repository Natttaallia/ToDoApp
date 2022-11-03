package com.example.todo.app.data

sealed class Status<out T> {
    object Loading : Status<Nothing>()
    data class Success<out T>(val data: T) : Status<T>()
}