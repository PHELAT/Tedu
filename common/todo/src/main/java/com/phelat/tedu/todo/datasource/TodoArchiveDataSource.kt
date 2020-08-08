package com.phelat.tedu.todo.datasource

import com.phelat.tedu.datasource.Deletable
import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.dependencyinjection.common.CommonScope
import com.phelat.tedu.functional.Failure
import com.phelat.tedu.functional.Response
import com.phelat.tedu.functional.Success
import com.phelat.tedu.mapper.Mapper
import com.phelat.tedu.todo.database.dao.TodoEntityDao
import com.phelat.tedu.todo.database.entity.TodoDatabaseEntity
import com.phelat.tedu.todo.entity.TodoEntity
import com.phelat.tedu.todo.error.TodoArchivableErrorContext
import com.phelat.tedu.todo.type.ArchivableTodos
import java.util.Date
import javax.inject.Inject

@CommonScope
internal class TodoArchiveDataSource @Inject constructor(
    private val todoEntityDao: TodoEntityDao,
    private val mapper: Mapper<TodoDatabaseEntity, TodoEntity>
) : ArchivableTodoReadable, ArchivableTodoDeletable {

    override suspend fun read(input: Date): ArchivableTodos {
        return todoEntityDao.selectAllDoneTodosBefore(input)
            .map(mapper::mapFirstToSecond)
    }

    override suspend fun delete(
        input: ArchivableTodos
    ): Response<Unit, TodoArchivableErrorContext> {
        val todoDatabaseEntities = input.map(mapper::mapSecondToFirst)
        val affectedRows = todoEntityDao.deleteTodos(todoDatabaseEntities)
        return if (affectedRows > 0) {
            Success(Unit)
        } else {
            Failure(TodoArchivableErrorContext.DeletionFailed())
        }
    }
}

typealias ArchivableTodoReadable = Readable.Suspendable.IO<Date,
        @JvmSuppressWildcards ArchivableTodos>

typealias ArchivableTodoDeletable = Deletable.Suspendable.IO<@JvmSuppressWildcards ArchivableTodos,
        @JvmSuppressWildcards Response<Unit, TodoArchivableErrorContext>>