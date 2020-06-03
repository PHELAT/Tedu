package com.phelat.tedu.todo.datasource

import com.phelat.tedu.datasource.Writable
import com.phelat.tedu.dependencyinjection.common.CommonScope
import com.phelat.tedu.mapper.Mapper
import com.phelat.tedu.todo.database.dao.ActionEntityDao
import com.phelat.tedu.todo.database.entity.ActionDatabaseEntity
import com.phelat.tedu.todo.entity.ActionEntity
import javax.inject.Inject

@CommonScope
class TodoActionsDataSource @Inject constructor(
    private val actionsDao: ActionEntityDao,
    private val mapper: Mapper<ActionEntity, ActionDatabaseEntity>
) : Writable<ActionEntity> {

    override fun write(input: ActionEntity) {
        val actionDatabaseEntity = mapper.mapFirstToSecond(input)
        actionsDao.insertAction(actionDatabaseEntity)
    }
}