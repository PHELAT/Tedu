package com.phelat.tedu.todo.datasource

import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.datasource.Writable
import com.phelat.tedu.dependencyinjection.common.CommonScope
import com.phelat.tedu.functional.Failure
import com.phelat.tedu.functional.Response
import com.phelat.tedu.functional.Success
import com.phelat.tedu.functional.mapForEach
import com.phelat.tedu.mapper.Mapper
import com.phelat.tedu.todo.database.dao.ActionEntityDao
import com.phelat.tedu.todo.database.entity.ActionDatabaseEntity
import com.phelat.tedu.todo.entity.ActionEntity
import com.phelat.tedu.todo.error.TodoErrorContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@CommonScope
internal class TodoActionsDataSource @Inject constructor(
    private val actionsDao: ActionEntityDao,
    private val mapper: Mapper<ActionEntity, ActionDatabaseEntity>
) : TodoActionsWritable,
    TodoActionsReadable {

    override suspend fun write(input: ActionEntity): Response<Unit, TodoErrorContext> {
        return mapper.mapFirstToSecond(input)
            .let { entity -> actionsDao.insertAction(entity) }
            .takeIf { actionId -> actionId >= 0 }
            ?.let { Success(Unit) }
            ?: Failure(TodoErrorContext.ActionInsertionFailed())
    }

    override fun read(): Flow<List<ActionEntity>> {
        return actionsDao.queryActions()
            .mapForEach(mapper::mapSecondToFirst)
    }
}

typealias TodoActionsWritable = Writable.Suspendable.IO<ActionEntity,
        @JvmSuppressWildcards Response<Unit, TodoErrorContext>>

typealias TodoActionsReadable = Readable<@JvmSuppressWildcards Flow<List<ActionEntity>>>