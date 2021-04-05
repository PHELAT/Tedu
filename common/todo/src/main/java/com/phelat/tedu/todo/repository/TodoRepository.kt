package com.phelat.tedu.todo.repository

import com.phelat.tedu.functional.Response
import com.phelat.tedu.todo.entity.ActionEntity
import com.phelat.tedu.todo.entity.TodoEntity
import com.phelat.tedu.todo.error.TodoErrorContext
import kotlinx.coroutines.flow.Flow

interface TodoRepository {

    suspend fun processAction(entity: ActionEntity): Response<Unit, TodoErrorContext>

    fun getFutureTodos(): Flow<List<TodoEntity>>

    fun getTodayTodos(): Flow<List<TodoEntity>>
}