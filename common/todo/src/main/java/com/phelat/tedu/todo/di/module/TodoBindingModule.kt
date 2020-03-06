package com.phelat.tedu.todo.di.module

import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.datasource.Updatable
import com.phelat.tedu.datasource.Writable
import com.phelat.tedu.mapper.Mapper
import com.phelat.tedu.todo.database.entity.TodoDatabaseEntity
import com.phelat.tedu.todo.datasource.TodoDataSource
import com.phelat.tedu.todo.di.scope.TodoScope
import com.phelat.tedu.todo.entity.TodoEntity
import com.phelat.tedu.todo.mapper.TodoDatabaseEntityToTodoEntity
import dagger.Binds
import dagger.Module
import kotlinx.coroutines.flow.Flow

@Module
internal interface TodoBindingModule {

    @TodoScope
    @Binds
    fun bindTodoDataSourceToWritable(todoDataSource: TodoDataSource): Writable.Suspendable<TodoEntity>

    @TodoScope
    @Binds
    fun bindTodoDataSourceToReadable(todoDataSource: TodoDataSource): Readable<Flow<List<TodoEntity>>>

    @TodoScope
    @Binds
    fun bindTodoDataSourceToUpdatable(todoDataSource: TodoDataSource): Updatable.Suspendable<TodoEntity>

    @TodoScope
    @Binds
    fun bindMapperToTodoDatabaseEntityToTodoEntity(
        mapper: TodoDatabaseEntityToTodoEntity
    ): Mapper<TodoDatabaseEntity, TodoEntity>
}