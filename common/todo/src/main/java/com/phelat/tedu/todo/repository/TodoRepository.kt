package com.phelat.tedu.todo.repository

import com.phelat.tedu.functional.Response
import com.phelat.tedu.todo.entity.TodoEntity
import com.phelat.tedu.todo.error.TodoErrorContext
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface TodoRepository {

    suspend fun addTodo(todoEntity: TodoEntity): Response<Unit, TodoErrorContext>

    suspend fun updateTodo(todoEntity: TodoEntity): Response<Unit, TodoErrorContext>

    suspend fun deleteTodo(todoEntity: TodoEntity): Response<Unit, TodoErrorContext>

    fun getTodos(date: Date): Flow<List<TodoEntity>>
}