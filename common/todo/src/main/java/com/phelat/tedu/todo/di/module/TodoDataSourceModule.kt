package com.phelat.tedu.todo.di.module

import com.phelat.tedu.dependencyinjection.common.CommonScope
import com.phelat.tedu.todo.datasource.TodoActionsDataSource
import com.phelat.tedu.todo.datasource.TodoActionsReadable
import com.phelat.tedu.todo.datasource.TodoActionsWritable
import com.phelat.tedu.todo.datasource.TodoDataSource
import com.phelat.tedu.todo.datasource.TodoDataSourceDeletable
import com.phelat.tedu.todo.datasource.TodoDataSourceReadable
import com.phelat.tedu.todo.datasource.TodoDataSourceUpdatable
import com.phelat.tedu.todo.datasource.TodoDataSourceWritable
import com.phelat.tedu.todo.repository.TodoRepository
import com.phelat.tedu.todo.repository.TodoSyncRepository
import dagger.Binds
import dagger.Module

@Module
internal interface TodoDataSourceModule {

    @Binds
    @CommonScope
    fun bindTodoDataSourceToWritable(input: TodoDataSource): TodoDataSourceWritable

    @Binds
    @CommonScope
    fun bindTodoDataSourceToReadable(input: TodoDataSource): TodoDataSourceReadable

    @Binds
    @CommonScope
    fun bindTodoDataSourceToUpdatable(input: TodoDataSource): TodoDataSourceUpdatable

    @Binds
    @CommonScope
    fun bindTodoDataSourceToDeletable(input: TodoDataSource): TodoDataSourceDeletable

    @Binds
    @CommonScope
    fun bindTodoActionsDataSourceWritable(input: TodoActionsDataSource): TodoActionsWritable

    @Binds
    @CommonScope
    fun bindTodoActionsDataSourceReadable(input: TodoActionsDataSource): TodoActionsReadable

    @Binds
    @CommonScope
    fun bindTodoSyncRepository(input: TodoSyncRepository): TodoRepository
}