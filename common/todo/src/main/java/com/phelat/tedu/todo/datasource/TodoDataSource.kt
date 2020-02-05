package com.phelat.tedu.todo.datasource

import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.datasource.Writable
import com.phelat.tedu.todo.database.dao.TodoEntityDao
import com.phelat.tedu.todo.database.entity.TodoDatabaseEntity
import com.phelat.tedu.todo.di.scope.TodoScope
import com.phelat.tedu.todo.entity.TodoEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@TodoScope
class TodoDataSource @Inject constructor(
    private val todoEntityDao: TodoEntityDao
) : Writable<TodoEntity>, Readable<@JvmSuppressWildcards Flow<List<TodoEntity>>> {

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

    override fun read(): Flow<List<TodoEntity>> {
        return todoEntityDao.selectAllTodos()
            .map { todoDatabaseEntities ->
                todoDatabaseEntities.map {
                    TodoEntity(it.todoId ?: -1, it.todo)
                }
            }
    }
}