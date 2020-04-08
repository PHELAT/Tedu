package com.phelat.tedu.todo.di.expose

import com.phelat.tedu.datasource.Deletable
import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.datasource.Updatable
import com.phelat.tedu.datasource.Writable
import com.phelat.tedu.functional.Response
import com.phelat.tedu.todo.entity.TodoEntity
import com.phelat.tedu.todo.error.TodoErrorContext
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface ExposeTodoDataSource {

    fun exposeTodoWritableDataSource(): Writable.Suspendable.IO<TodoEntity, Response<Unit, TodoErrorContext>>

    fun exposeTodoReadableDataSource(): Readable.IO<Date, Flow<List<TodoEntity>>>

    fun exposeTodoUpdatableDataSource(): Updatable.Suspendable.IO<TodoEntity, Response<Unit, TodoErrorContext>>

    fun exposeTodoDeletableDataSource(): Deletable.Suspendable.IO<TodoEntity, Response<Unit, TodoErrorContext>>
}