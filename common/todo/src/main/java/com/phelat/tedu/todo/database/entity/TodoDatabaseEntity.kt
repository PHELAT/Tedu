package com.phelat.tedu.todo.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "todo_table")
internal data class TodoDatabaseEntity(
    @PrimaryKey(autoGenerate = true)
    val todoId: Int?,
    val todo: String,
    val isDone: Boolean,
    val date: Date
)