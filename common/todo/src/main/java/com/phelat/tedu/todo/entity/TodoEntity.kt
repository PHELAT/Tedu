package com.phelat.tedu.todo.entity

import java.io.Serializable

data class TodoEntity(
    val todo: String,
    val isDone: Boolean = false,
    val todoId: Int = -1
): Serializable