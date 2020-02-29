package com.phelat.tedu.todo.di.module

import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.datasource.Updatable
import com.phelat.tedu.datasource.Writable
import com.phelat.tedu.todo.datasource.TodoDataSource
import com.phelat.tedu.todo.di.scope.TodoScope
import com.phelat.tedu.todo.entity.TodoEntity
import dagger.Binds
import dagger.Module
import kotlinx.coroutines.flow.Flow

@Module
interface TodoBindingModule {

    @TodoScope
    @Binds
    fun bindTodoDataSourceToWritable(todoDataSource: TodoDataSource): Writable.Suspendable<TodoEntity>

    @TodoScope
    @Binds
    fun bindTodoDataSourceToReadable(todoDataSource: TodoDataSource): Readable<Flow<List<TodoEntity>>>

    @TodoScope
    @Binds
    fun bindTodoDataSourceToUpdatable(todoDataSource: TodoDataSource): Updatable.Suspendable<TodoEntity>
}