package com.phelat.tedu.todo.di.module

import com.phelat.tedu.datasource.Deletable
import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.datasource.Updatable
import com.phelat.tedu.datasource.Writable
import com.phelat.tedu.dependencyinjection.common.CommonScope
import com.phelat.tedu.functional.Response
import com.phelat.tedu.todo.datasource.TodoDataSource
import com.phelat.tedu.todo.entity.TodoEntity
import com.phelat.tedu.todo.error.TodoErrorContext
import dagger.Binds
import dagger.Module
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Module
internal interface TodoDataSourceModule {

    @Binds
    @CommonScope
    fun bindTodoDataSourceToWritable(
        input: TodoDataSource
    ): Writable.Suspendable.IO<TodoEntity, Response<Unit, TodoErrorContext>>

    @Binds
    @CommonScope
    fun bindTodoDataSourceToReadable(
        input: TodoDataSource
    ): Readable.IO<Date, Flow<List<TodoEntity>>>

    @Binds
    @CommonScope
    fun bindTodoDataSourceToUpdatable(
        input: TodoDataSource
    ): Updatable.Suspendable.IO<TodoEntity, Response<Unit, TodoErrorContext>>

    @Binds
    @CommonScope
    fun bindTodoDataSourceToDeletable(
        input: TodoDataSource
    ): Deletable.Suspendable.IO<TodoEntity, Response<Unit, TodoErrorContext>>
}