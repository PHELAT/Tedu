package com.phelat.tedu.todo.entity

data class TodoEntity(
    val todo: String,
    val isDone: Boolean = false,
    val todoId: Int = -1
)