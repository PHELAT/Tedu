package com.phelat.tedu.todo.entity

data class ActionEntity(
    val action: Action,
    val timestamp: Long,
    val data: TodoEntity
) : Comparable<ActionEntity> {

    override fun compareTo(other: ActionEntity): Int {
        return when {
            timestamp > other.timestamp -> {
                1
            }
            timestamp == other.timestamp -> {
                0
            }
            else -> {
                -1
            }
        }
    }
}