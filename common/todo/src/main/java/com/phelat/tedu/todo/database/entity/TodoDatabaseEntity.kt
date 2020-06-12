package com.phelat.tedu.todo.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "todo_table")
internal data class TodoDatabaseEntity(
    @PrimaryKey(autoGenerate = false)
    val todoId: Long,
    val todo: String,
    val isDone: Boolean,
    val date: Date
)