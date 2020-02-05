package com.phelat.tedu.todo.datasource

import com.phelat.tedu.datasource.Writable
import com.phelat.tedu.todo.database.dao.TodoEntityDao
import com.phelat.tedu.todo.database.entity.TodoDatabaseEntity
import com.phelat.tedu.todo.di.scope.TodoScope
import com.phelat.tedu.todo.entity.TodoEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@TodoScope
class TodoDataSource @Inject constructor(
    private val todoEntityDao: TodoEntityDao
) : Writable<TodoEntity> {

    override fun write(input: TodoEntity) {
        val todoDatabaseEntity = TodoDatabaseEntity(
            todoId = input.todoId.takeIf { it > -1 },
            todo = input.todo
        )
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                todoEntityDao.addTodo(todoDatabaseEntity)
            }
        }
    }
}