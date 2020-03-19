package com.phelat.tedu.todo.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.phelat.tedu.todo.database.entity.TodoDatabaseEntity
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
internal interface TodoEntityDao {

    @Insert(entity = TodoDatabaseEntity::class)
    suspend fun insertTodo(todo: TodoDatabaseEntity): Long

    @Query("SELECT * FROM todo_table WHERE date < :before ORDER BY isDone ASC, todoId DESC")
    fun selectAllTodosBefore(before: Date): Flow<List<TodoDatabaseEntity>>

    @Query("SELECT * FROM todo_table WHERE date < :before AND isDone = 1")
    suspend fun selectAllDoneTodosBefore(before: Date): List<TodoDatabaseEntity>

    @Update(entity = TodoDatabaseEntity::class)
    suspend fun updateTodo(todo: TodoDatabaseEntity): Int

    @Delete(entity = TodoDatabaseEntity::class)
    suspend fun deleteTodo(todo: TodoDatabaseEntity): Int

    @Delete(entity = TodoDatabaseEntity::class)
    suspend fun deleteTodos(todo: List<TodoDatabaseEntity>): Int
}