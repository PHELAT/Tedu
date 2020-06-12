package com.phelat.tedu.todo.mapper

import com.phelat.tedu.dependencyinjection.common.CommonScope
import com.phelat.tedu.mapper.Mapper
import com.phelat.tedu.todo.database.entity.TodoDatabaseEntity
import com.phelat.tedu.todo.entity.TodoEntity
import javax.inject.Inject

@CommonScope
internal class TodoDatabaseEntityToTodoEntity @Inject constructor() :
    Mapper<TodoDatabaseEntity, TodoEntity> {

    override fun mapFirstToSecond(first: TodoDatabaseEntity): TodoEntity {
        return TodoEntity(
            todo = first.todo,
            isDone = first.isDone,
            todoId = requireNotNull(first.todoId),
            date = first.date
        )
    }

    override fun mapSecondToFirst(second: TodoEntity): TodoDatabaseEntity {
        return TodoDatabaseEntity(
            todoId = second.todoId,
            todo = second.todo,
            isDone = second.isDone,
            date = second.date
        )
    }
}