package com.phelat.tedu.todo.mapper

import com.phelat.tedu.mapper.Mapper
import com.phelat.tedu.todo.database.entity.TodoDatabaseEntity
import com.phelat.tedu.todo.di.scope.TodoScope
import com.phelat.tedu.todo.entity.TodoEntity
import javax.inject.Inject

@TodoScope
internal class TodoDatabaseEntityToTodoEntity @Inject constructor() :
    Mapper<TodoDatabaseEntity, TodoEntity> {

    override fun mapFirstToSecond(first: TodoDatabaseEntity): TodoEntity {
        return TodoEntity(
            todo = first.todo,
            isDone = first.isDone,
            todoId = requireNotNull(first.todoId)
        )
    }

    override fun mapSecondToFirst(second: TodoEntity): TodoDatabaseEntity {
        return TodoDatabaseEntity(
            todoId = second.todoId.takeIf { it > -1 },
            todo = second.todo,
            isDone = second.isDone
        )
    }
}