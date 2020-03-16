package com.phelat.tedu.todo.di.module

import com.phelat.tedu.datasource.Deletable
import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.datasource.Updatable
import com.phelat.tedu.datasource.Writable
import com.phelat.tedu.todo.datasource.TodoDataSource
import com.phelat.tedu.todo.di.scope.TodoScope
import com.phelat.tedu.todo.entity.TodoEntity
import dagger.Binds
import dagger.Module
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Module
internal interface TodoDataSourceModule {

    @Binds
    @TodoScope
    fun bindTodoDataSourceToWritable(input: TodoDataSource): Writable.Suspendable<TodoEntity>

    @Binds
    @TodoScope
    fun bindTodoDataSourceToReadable(input: TodoDataSource): Readable.IO<@JvmSuppressWildcards Date, Flow<List<TodoEntity>>>

    @Binds
    @TodoScope
    fun bindTodoDataSourceToUpdatable(input: TodoDataSource): Updatable.Suspendable<TodoEntity>

    @Binds
    @TodoScope
    fun bindTodoDataSourceToDeletable(input: TodoDataSource): Deletable.Suspendable<TodoEntity>
}