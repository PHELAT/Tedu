package com.phelat.tedu.todo.di.module

import com.phelat.tedu.datasource.Writable
import com.phelat.tedu.todo.database.dao.TodoEntityDao
import com.phelat.tedu.todo.datasource.TodoDataSource
import com.phelat.tedu.todo.entity.TodoEntity
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class TodoBindingModule {

    @Module
    companion object {
        @Provides
        fun provideTodoDataSource(todoEntityDao: TodoEntityDao): TodoDataSource {
            return TodoDataSource(todoEntityDao)
        }
    }

    @Binds
    abstract fun bindTodoDataSource(todoDataSource: TodoDataSource): Writable<TodoEntity>
}