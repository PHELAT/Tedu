package com.phelat.tedu.todo.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_table")
data class TodoDatabaseEntity(
    @PrimaryKey(autoGenerate = true)
    val todoId: Int,
    val todo: String
)