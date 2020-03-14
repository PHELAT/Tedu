package com.phelat.tedu.todo.mapper

import com.phelat.tedu.mapper.Mapper
import com.phelat.tedu.todo.database.entity.TodoDatabaseEntity
import com.phelat.tedu.todo.entity.TodoEntity

internal class TodoDatabaseEntityToTodoEntity : Mapper<TodoDatabaseEntity, TodoEntity> {

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
            todoId = second.todoId.takeIf { it > -1 },
            todo = second.todo,
            isDone = second.isDone,
            date = second.date
        )
    }
}