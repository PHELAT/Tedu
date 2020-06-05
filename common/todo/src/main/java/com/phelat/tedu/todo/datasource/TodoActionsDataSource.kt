package com.phelat.tedu.todo.datasource

import com.phelat.tedu.datasource.Writable
import com.phelat.tedu.dependencyinjection.common.CommonScope
import com.phelat.tedu.functional.Failure
import com.phelat.tedu.functional.Response
import com.phelat.tedu.functional.Success
import com.phelat.tedu.mapper.Mapper
import com.phelat.tedu.todo.database.dao.ActionEntityDao
import com.phelat.tedu.todo.database.entity.ActionDatabaseEntity
import com.phelat.tedu.todo.entity.ActionEntity
import com.phelat.tedu.todo.error.TodoErrorContext
import javax.inject.Inject

@CommonScope
class TodoActionsDataSource @Inject constructor(
    private val actionsDao: ActionEntityDao,
    private val mapper: Mapper<ActionEntity, ActionDatabaseEntity>
) : Writable.Suspendable.IO<ActionEntity, @JvmSuppressWildcards Response<Unit, TodoErrorContext>> {

    override suspend fun write(input: ActionEntity): Response<Unit, TodoErrorContext> {
        return mapper.mapFirstToSecond(input)
            .let { entity -> actionsDao.insertAction(entity) }
            .takeIf { actionId -> actionId >= 0 }
            ?.let { Success(Unit) }
            ?: Failure(TodoErrorContext.ActionInsertionFailed)
    }
}