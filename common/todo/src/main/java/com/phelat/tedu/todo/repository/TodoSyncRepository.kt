package com.phelat.tedu.todo.repository

import com.phelat.tedu.datasource.Deletable
import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.datasource.Updatable
import com.phelat.tedu.datasource.Writable
import com.phelat.tedu.dependencyinjection.common.CommonScope
import com.phelat.tedu.functional.Failure
import com.phelat.tedu.functional.Response
import com.phelat.tedu.functional.getFailureResponse
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
    private val todoUpdatable: Updatable.Suspendable.IO<TodoEntity,
            Response<Unit, TodoErrorContext>>,
    private val todoDeletable: Deletable.Suspendable.IO<TodoEntity,
            Response<Unit, TodoErrorContext>>,
    private val actionsWritable: Writable.Suspendable.IO<ActionEntity,
            Response<Unit, TodoErrorContext>>
) : TodoRepository {

    override suspend fun processAction(entity: ActionEntity): Response<Unit, TodoErrorContext> {
        return when (entity.action) {
            is Action.Add -> addTodo(entity)
            is Action.Update -> updateTodo(entity)
            is Action.Delete -> deleteTodo(entity)
            else -> Failure(TodoErrorContext.UndefinedAction())
        }
    }

    private suspend fun addTodo(entity: ActionEntity): Response<Unit, TodoErrorContext> {
        val todoWriteResult = todoWritable.write(entity.data)
        todoWriteResult.ifSuccessful {
            return actionsWritable.write(entity)
        }
        return todoWriteResult.getFailureResponse()
    }

    private suspend fun updateTodo(entity: ActionEntity): Response<Unit, TodoErrorContext> {
        val todoUpdateResult = todoUpdatable.update(entity.data)
        todoUpdateResult.ifSuccessful {
            return actionsWritable.write(entity)
        }
        return todoUpdateResult
    }

    private suspend fun deleteTodo(entity: ActionEntity): Response<Unit, TodoErrorContext> {
        val todoDeleteResult = todoDeletable.delete(entity.data)
        todoDeleteResult.ifSuccessful {
            return actionsWritable.write(entity)
        }
        return todoDeleteResult
    }

    override fun getTodos(date: Date): Flow<List<TodoEntity>> {
        return todoReadable.read(date)
    }
}