package com.phelat.tedu.todo.datasource

import com.phelat.tedu.datasource.Deletable
import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.datasource.Updatable
import com.phelat.tedu.datasource.Writable
import com.phelat.tedu.dependencyinjection.common.CommonScope
import com.phelat.tedu.functional.Failure
import com.phelat.tedu.functional.Response
import com.phelat.tedu.functional.Success
import com.phelat.tedu.functional.mapForEach
import com.phelat.tedu.mapper.Mapper
import com.phelat.tedu.todo.database.dao.TodoEntityDao
import com.phelat.tedu.todo.database.entity.TodoDatabaseEntity
import com.phelat.tedu.todo.entity.TodoEntity
import com.phelat.tedu.todo.error.TodoErrorContext
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject

@CommonScope
internal class TodoDataSource @Inject constructor(
    private val todoEntityDao: TodoEntityDao,
    private val mapper: Mapper<TodoDatabaseEntity, TodoEntity>
) : TodoDataSourceWritable,
    TodoDataSourceReadable,
    TodoDataSourceUpdatable,
    TodoDataSourceDeletable {

    override suspend fun write(input: TodoEntity): Response<Unit, TodoErrorContext> {
        val todoDatabaseEntity = mapper.mapSecondToFirst(input)
        val todoId = todoEntityDao.insertTodo(todoDatabaseEntity)
        return if (todoId >= 0) {
            Success(Unit)
        } else {
            Failure(TodoErrorContext.InsertionFailed())
        }
    }

    override fun read(input: Date): Flow<List<TodoEntity>> {
        return todoEntityDao.selectAllTodosBefore(input)
            .mapForEach(mapper::mapFirstToSecond)
    }

    override suspend fun update(input: TodoEntity): Response<Unit, TodoErrorContext> {
        val todoDatabaseEntity = mapper.mapSecondToFirst(input)
        val affectedRows = todoEntityDao.updateTodo(todoDatabaseEntity)
        return if (affectedRows > 0) {
            Success(Unit)
        } else {
            Failure(TodoErrorContext.UpdateFailed())
        }
    }

    override suspend fun delete(input: TodoEntity): Response<Unit, TodoErrorContext> {
        val todoDatabaseEntity = mapper.mapSecondToFirst(input)
        val affectedRows = todoEntityDao.deleteTodo(todoDatabaseEntity)
        return if (affectedRows > 0) {
            Success(Unit)
        } else {
            Failure(TodoErrorContext.DeletionFailed())
        }
    }
}

typealias TodoDataSourceDeletable = Deletable.Suspendable.IO<TodoEntity,
        @JvmSuppressWildcards Response<Unit, TodoErrorContext>>

typealias TodoDataSourceUpdatable = Updatable.Suspendable.IO<TodoEntity,
        @JvmSuppressWildcards Response<Unit, TodoErrorContext>>

typealias TodoDataSourceReadable = Readable.IO<Date, Flow<@JvmSuppressWildcards List<TodoEntity>>>

typealias TodoDataSourceWritable = Writable.Suspendable.IO<TodoEntity,
        @JvmSuppressWildcards Response<Unit, TodoErrorContext>>