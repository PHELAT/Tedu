package com.phelat.tedu.todo.datasource

import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.datasource.Updatable
import com.phelat.tedu.datasource.Writable
import com.phelat.tedu.mapper.Mapper
import com.phelat.tedu.todo.database.dao.TodoEntityDao
import com.phelat.tedu.todo.database.entity.TodoDatabaseEntity
import com.phelat.tedu.todo.di.scope.TodoScope
import com.phelat.tedu.todo.entity.TodoEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@TodoScope
internal class TodoDataSource @Inject constructor(
    private val todoEntityDao: TodoEntityDao,
    private val mapper: Mapper<TodoDatabaseEntity, TodoEntity>
) : Writable.Suspendable<TodoEntity>,
    Readable<@JvmSuppressWildcards Flow<List<TodoEntity>>>,
    Updatable.Suspendable<TodoEntity> {

    override suspend fun write(input: TodoEntity) {
        val todoDatabaseEntity = mapper.mapSecondToFirst(input)
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
        val todoDatabaseEntity = mapper.mapSecondToFirst(input)
        todoEntityDao.updateTodo(todoDatabaseEntity)
    }
}