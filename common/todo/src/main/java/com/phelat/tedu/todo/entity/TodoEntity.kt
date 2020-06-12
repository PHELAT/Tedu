package com.phelat.tedu.todo.entity

import java.io.Serializable
import java.util.Date

data class TodoEntity(
    val todo: String,
    val date: Date,
    val todoId: Long,
    val isDone: Boolean = false
) : Serializable