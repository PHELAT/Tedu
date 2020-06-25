package com.phelat.tedu.todo.mapper

import com.phelat.tedu.dependencyinjection.common.CommonScope
import com.phelat.tedu.mapper.Mapper
import com.phelat.tedu.todo.database.entity.ActionDatabaseEntity
import com.phelat.tedu.todo.entity.ActionEntity
import com.phelat.tedu.todo.entity.TodoEntity
import javax.inject.Inject

@CommonScope
class ActionEntityToActionDatabaseEntity @Inject constructor() :
    Mapper<ActionEntity, ActionDatabaseEntity> {

    override fun mapFirstToSecond(first: ActionEntity): ActionDatabaseEntity {
        return ActionDatabaseEntity(
            actionId = null,
            action = first.action,
            timestamp = first.timestamp,
            todoId = first.data.todoId,
            todo = first.data.todo,
            isDone = first.data.isDone,
            date = first.data.date
        )
    }

    override fun mapSecondToFirst(second: ActionDatabaseEntity): ActionEntity {
        return ActionEntity(
            action = second.action,
            timestamp = second.timestamp,
            data = TodoEntity(
                todo = second.todo,
                date = second.date,
                isDone = second.isDone,
                todoId = second.todoId
            )
        )
    }
}