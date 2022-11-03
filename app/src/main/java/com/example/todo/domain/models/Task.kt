package com.example.todo.domain.models

import java.util.*

data class Task(
    var id: String = UUID.randomUUID().toString(),
    var title: String = "",
    var description: String = "",
    var isCompleted: Boolean = false
)