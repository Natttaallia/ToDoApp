package com.example.todo.domain.models

import java.util.*

data class Task(var id: String = UUID.randomUUID().toString())