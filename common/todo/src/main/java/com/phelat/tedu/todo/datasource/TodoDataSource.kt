package com.phelat.tedu.todo.datasource

import com.phelat.tedu.datasource.Deletable
import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.datasource.Updatable
import com.phelat.tedu.datasource.Writable
import com.phelat.tedu.dependencyinjection.scope.CommonScope
import com.phelat.tedu.mapper.Mapper
import com.phelat.tedu.todo.database.dao.TodoEntityDao
import com.phelat.tedu.todo.database.entity.TodoDatabaseEntity
import com.phelat.tedu.todo.entity.TodoEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Date
import javax.inject.Inject

@CommonScope
internal class TodoDataSource @Inject constructor(
    private val todoEntityDao: TodoEntityDao,
    private val mapper: Mapper<TodoDatabaseEntity, TodoEntity>
) : Writable.Suspendable<TodoEntity>,
    Readable.IO<Date, @JvmSuppressWildcards Flow<@JvmSuppressWildcards List<TodoEntity>>>,
    Updatable.Suspendable<TodoEntity>,
    Deletable.Suspendable<TodoEntity> {

    override suspend fun write(input: TodoEntity) {
        val todoDatabaseEntity = mapper.mapSecondToFirst(input)
        todoEntityDao.insertTodo(todoDatabaseEntity)
    }

    override fun read(input: Date): Flow<List<TodoEntity>> {
        return todoEntityDao.selectAllTodosBefore(input)
            .map { todoDatabaseEntities -> todoDatabaseEntities.map(mapper::mapFirstToSecond) }
    }

    override suspend fun update(input: TodoEntity) {
        val todoDatabaseEntity = mapper.mapSecondToFirst(input)
        todoEntityDao.updateTodo(todoDatabaseEntity)
    }

    override suspend fun delete(input: TodoEntity) {
        val todoDatabaseEntity = mapper.mapSecondToFirst(input)
        todoEntityDao.deleteTodo(todoDatabaseEntity)
    }
}