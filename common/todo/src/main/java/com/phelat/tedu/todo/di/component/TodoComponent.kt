package com.phelat.tedu.todo.di.component

import com.phelat.tedu.androidcore.di.component.AndroidCoreComponent
import com.phelat.tedu.dependencyinjection.StartupTasks
import com.phelat.tedu.dependencyinjection.common.CommonScope
import com.phelat.tedu.mapper.Mapper
import com.phelat.tedu.todo.datasource.ArchivableTodoDeletable
import com.phelat.tedu.todo.datasource.ArchivableTodoReadable
import com.phelat.tedu.todo.datasource.TodoActionsReadable
import com.phelat.tedu.todo.datasource.TodoActionsWritable
import com.phelat.tedu.todo.di.module.TodoArchiveDataSourceModule
import com.phelat.tedu.todo.di.module.TodoDataSourceModule
import com.phelat.tedu.todo.di.module.TodoDatabaseModule
import com.phelat.tedu.todo.di.module.TodoMapperModule
import com.phelat.tedu.todo.di.module.TodoStartupTasksModule
import com.phelat.tedu.todo.di.qualifier.TodoStartupTasks
import com.phelat.tedu.todo.repository.TodoRepository
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
interface TodoComponent {

    fun exposeTodoArchiveReadableDataSource(): ArchivableTodoReadable

    fun exposeTodoArchiveDeletableDataSource(): ArchivableTodoDeletable

    fun exposeMapperDateToLocalDate(): Mapper<Date, LocalDate>

    fun exposeTodoSyncRepository(): TodoRepository

    fun exposeTodoActionsReadable(): TodoActionsReadable

    fun exposeTodoActionsWritable(): TodoActionsWritable

    @TodoStartupTasks
    fun getTodoStartupTasks(): StartupTasks
}