package com.phelat.tedu.todo.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.phelat.tedu.todo.database.entity.TodoDatabaseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoEntityDao {

    @Insert(entity = TodoDatabaseEntity::class)
    suspend fun insertTodo(todo: TodoDatabaseEntity): Long

    @Query("SELECT * FROM todo_table ORDER BY isDone ASC, todoId DESC")
    fun selectAllTodos(): Flow<List<TodoDatabaseEntity>>

    @Update(entity = TodoDatabaseEntity::class)
    suspend fun updateTodo(todo: TodoDatabaseEntity): Int
}