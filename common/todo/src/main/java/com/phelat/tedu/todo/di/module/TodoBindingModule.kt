package com.phelat.tedu.todo.di.module

import com.phelat.tedu.datasource.Writable
import com.phelat.tedu.todo.datasource.TodoDataSource
import com.phelat.tedu.todo.di.scope.TodoScope
import com.phelat.tedu.todo.entity.TodoEntity
import dagger.Binds
import dagger.Module

@Module
abstract class TodoBindingModule {

    @TodoScope
    @Binds
    abstract fun bindTodoDataSource(todoDataSource: TodoDataSource): Writable<TodoEntity>
}