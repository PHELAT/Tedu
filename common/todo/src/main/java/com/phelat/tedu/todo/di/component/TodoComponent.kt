package com.phelat.tedu.todo.di.component

import com.phelat.tedu.androidcore.di.component.AndroidCoreComponent
import com.phelat.tedu.datasource.Deletable
import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.dependencyinjection.StartupTasks
import com.phelat.tedu.dependencyinjection.common.CommonScope
import com.phelat.tedu.functional.Response
import com.phelat.tedu.mapper.Mapper
import com.phelat.tedu.todo.di.expose.ExposeTodoDataSource
import com.phelat.tedu.todo.di.module.TodoArchiveDataSourceModule
import com.phelat.tedu.todo.di.module.TodoDataSourceModule
import com.phelat.tedu.todo.di.module.TodoDatabaseModule
import com.phelat.tedu.todo.di.module.TodoMapperModule
import com.phelat.tedu.todo.di.module.TodoStartupTasksModule
import com.phelat.tedu.todo.di.qualifier.TodoStartupTasks
import com.phelat.tedu.todo.error.TodoArchivableErrorContext
import com.phelat.tedu.todo.type.ArchivableTodos
import dagger.Component
import org.threeten.bp.LocalDate
import java.util.Date

@CommonScope
@Component(
    modules = [
        TodoDatabaseModule::class,
        TodoDataSourceModule::class,
        TodoMapperModule::class,
        TodoArchiveDataSourceModule::class,
        TodoStartupTasksModule::class
    ],
    dependencies = [AndroidCoreComponent::class]
)
interface TodoComponent : ExposeTodoDataSource {

    fun exposeTodoArchiveReadableDataSource(): Readable.Suspendable.IO<Date, ArchivableTodos>

    fun exposeTodoArchiveDeletableDataSource(): Deletable.Suspendable.IO<ArchivableTodos, Response<Unit, TodoArchivableErrorContext>>

    fun exposeMapperDateToLocalDate(): Mapper<Date, LocalDate>

    @TodoStartupTasks
    fun getTodoStartupTasks(): StartupTasks
}