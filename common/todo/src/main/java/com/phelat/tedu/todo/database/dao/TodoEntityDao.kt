package com.phelat.tedu.todo.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.phelat.tedu.todo.database.entity.TodoDatabaseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoEntityDao {

    @Insert(entity = TodoDatabaseEntity::class)
    suspend fun addTodo(todo: TodoDatabaseEntity): Long

    @Query("SELECT * FROM todo_table")
    fun selectAllTodos(): Flow<List<TodoDatabaseEntity>>
}