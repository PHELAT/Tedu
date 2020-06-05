package com.phelat.tedu.todo.repository

import com.phelat.tedu.datasource.Deletable
import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.datasource.Updatable
import com.phelat.tedu.datasource.Writable
import com.phelat.tedu.dependencyinjection.common.CommonScope
import com.phelat.tedu.functional.Response
import com.phelat.tedu.functional.ifSuccessful
import com.phelat.tedu.todo.entity.Action
import com.phelat.tedu.todo.entity.ActionEntity
import com.phelat.tedu.todo.entity.TodoEntity
import com.phelat.tedu.todo.error.TodoErrorContext
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject

@CommonScope
class TodoSyncRepository @Inject constructor(
    private val todoReadable: Readable.IO<Date, Flow<List<TodoEntity>>>,
    private val todoWritable: Writable.Suspendable.IO<TodoEntity, Response<Unit, TodoErrorContext>>,
    private val todoUpdatable: Updatable.Suspendable.IO<TodoEntity, Response<Unit, TodoErrorContext>>,
    private val todoDeletable: Deletable.Suspendable.IO<TodoEntity, Response<Unit, TodoErrorContext>>,
    private val actionsWritable: Writable.Suspendable.IO<ActionEntity, Response<Unit, TodoErrorContext>>
) : TodoRepository {

    override suspend fun addTodo(todoEntity: TodoEntity): Response<Unit, TodoErrorContext> {
        val todoWriteResult = todoWritable.write(todoEntity)
        todoWriteResult.ifSuccessful {
            return insertAction(Action.Add, todoEntity)
        }
        return todoWriteResult
    }

    override suspend fun updateTodo(todoEntity: TodoEntity): Response<Unit, TodoErrorContext> {
        val todoUpdateResult = todoUpdatable.update(todoEntity)
        todoUpdateResult.ifSuccessful {
            return insertAction(Action.Update, todoEntity)
        }
        return todoUpdateResult
    }

    override suspend fun deleteTodo(todoEntity: TodoEntity): Response<Unit, TodoErrorContext> {
        val todoDeleteResult = todoDeletable.delete(todoEntity)
        todoDeleteResult.ifSuccessful {
            return insertAction(Action.Delete, todoEntity)
        }
        return todoDeleteResult
    }

    override fun getTodos(date: Date): Flow<List<TodoEntity>> {
        return todoReadable.read(date)
    }

    private suspend fun insertAction(
        occurredAction: Action,
        entity: TodoEntity
    ): Response<Unit, TodoErrorContext> {
        val actionEntity = ActionEntity(
            action = occurredAction,
            timestamp = System.currentTimeMillis(),
            data = entity
        )
        return actionsWritable.write(actionEntity)
    }
}