package com.phelat.tedu.todo.datasource

import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.datasource.Updatable
import com.phelat.tedu.datasource.Writable
import com.phelat.tedu.todo.database.dao.TodoEntityDao
import com.phelat.tedu.todo.database.entity.TodoDatabaseEntity
import com.phelat.tedu.todo.di.scope.TodoScope
import com.phelat.tedu.todo.entity.TodoEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@TodoScope
class TodoDataSource @Inject constructor(
    private val todoEntityDao: TodoEntityDao
) : Writable.Suspendable<TodoEntity>,
    Readable<@JvmSuppressWildcards Flow<List<TodoEntity>>>,
    Updatable.Suspendable<TodoEntity> {

    override suspend fun write(input: TodoEntity) {
        // TODO: use a mapper class
        val todoDatabaseEntity = TodoDatabaseEntity(
            todoId = input.todoId.takeIf { it > -1 },
            todo = input.todo,
            isDone = input.isDone
        )
        todoEntityDao.insertTodo(todoDatabaseEntity)
    }

    override fun read(): Flow<List<TodoEntity>> {
        return todoEntityDao.selectAllTodos()
            .map { todoDatabaseEntities ->
                todoDatabaseEntities.map {
                    TodoEntity(todoId = it.todoId ?: -1, todo = it.todo, isDone = it.isDone)
                }
            }
    }

    override suspend fun update(input: TodoEntity) {
        val todoDatabaseEntity = TodoDatabaseEntity(
            todoId = input.todoId.takeIf { it > -1 },
            todo = input.todo,
            isDone = input.isDone
        )
        todoEntityDao.updateTodo(todoDatabaseEntity)
    }
}