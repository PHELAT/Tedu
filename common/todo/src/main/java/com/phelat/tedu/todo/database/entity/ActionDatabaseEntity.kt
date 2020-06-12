package com.phelat.tedu.todo.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.phelat.tedu.todo.entity.Action

@Entity(tableName = "action_table")
data class ActionDatabaseEntity(
    @PrimaryKey(autoGenerate = true)
    val actionId: Int?,
    val action: Action,
    val timestamp: Long,
    val todoId: Long
)