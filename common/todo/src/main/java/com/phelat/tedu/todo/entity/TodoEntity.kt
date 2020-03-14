package com.phelat.tedu.todo.entity

import java.io.Serializable
import java.util.Date

data class TodoEntity(
    val todo: String,
    val date: Date,
    val isDone: Boolean = false,
    val todoId: Int = -1
) : Serializable