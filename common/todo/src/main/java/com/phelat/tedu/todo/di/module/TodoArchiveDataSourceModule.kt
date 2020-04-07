package com.phelat.tedu.todo.di.module

import com.phelat.tedu.datasource.Deletable
import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.dependencyinjection.common.CommonScope
import com.phelat.tedu.functional.Response
import com.phelat.tedu.todo.datasource.TodoArchiveDataSource
import com.phelat.tedu.todo.error.TodoArchivableErrorContext
import com.phelat.tedu.todo.type.ArchivableTodos
import dagger.Binds
import dagger.Module
import java.util.Date

@Module
internal interface TodoArchiveDataSourceModule {

    @Binds
    @CommonScope
    fun bindTodoArchiveDataSourceToReadable(
        input: TodoArchiveDataSource
    ): Readable.Suspendable.IO<Date, ArchivableTodos>

    @Binds
    @CommonScope
    fun bindTodoArchiveDataSourceToDeletable(
        input: TodoArchiveDataSource
    ): Deletable.Suspendable.IO<ArchivableTodos, Response<Unit, TodoArchivableErrorContext>>
}