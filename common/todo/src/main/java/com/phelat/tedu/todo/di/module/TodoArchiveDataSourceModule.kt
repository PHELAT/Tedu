package com.phelat.tedu.todo.di.module

import com.phelat.tedu.dependencyinjection.common.CommonScope
import com.phelat.tedu.todo.datasource.ArchivableTodoDeletable
import com.phelat.tedu.todo.datasource.ArchivableTodoReadable
import com.phelat.tedu.todo.datasource.TodoArchiveDataSource
import dagger.Binds
import dagger.Module

@Module
internal interface TodoArchiveDataSourceModule {

    @Binds
    @CommonScope
    fun bindTodoArchiveDataSourceToReadable(input: TodoArchiveDataSource): ArchivableTodoReadable

    @Binds
    @CommonScope
    fun bindTodoArchiveDataSourceToDeletable(input: TodoArchiveDataSource): ArchivableTodoDeletable
}