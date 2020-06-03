package com.phelat.tedu.todo.entity

data class ActionEntity(
    val action: Action,
    val timestamp: Long,
    val data: TodoEntity
)