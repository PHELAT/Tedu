package com.phelat.tedu.todo.database.dao

import androidx.room.Dao
import androidx.room.Insert
import com.phelat.tedu.todo.database.entity.TodoDatabaseEntity

@Dao
interface TodoEntityDao {

    @Insert(entity = TodoDatabaseEntity::class)
    fun addTodo(todo: TodoDatabaseEntity)
}