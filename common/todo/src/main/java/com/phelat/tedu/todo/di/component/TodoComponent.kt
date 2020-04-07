package com.phelat.tedu.todo.di.component

import com.phelat.tedu.androidcore.di.component.AndroidCoreComponent
import com.phelat.tedu.androiddagger.CommonModule
import com.phelat.tedu.datasource.Deletable
import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.datasource.Updatable
import com.phelat.tedu.datasource.Writable
import com.phelat.tedu.dependencyinjection.common.CommonStartupComponent
import com.phelat.tedu.dependencyinjection.scope.CommonScope
import com.phelat.tedu.functional.Response
import com.phelat.tedu.mapper.Mapper
import com.phelat.tedu.todo.di.module.TodoArchiveDataSourceModule
import com.phelat.tedu.todo.di.module.TodoDataSourceModule
import com.phelat.tedu.todo.di.module.TodoDatabaseModule
import com.phelat.tedu.todo.di.module.TodoMapperModule
import com.phelat.tedu.todo.di.module.TodoStartupTasksModule
import com.phelat.tedu.todo.entity.TodoEntity
import com.phelat.tedu.todo.error.TodoArchivableErrorContext
import com.phelat.tedu.todo.error.TodoErrorContext
import com.phelat.tedu.todo.type.ArchivableTodos
import dagger.Component
import kotlinx.coroutines.flow.Flow
import org.threeten.bp.LocalDate
import java.util.Date

@CommonScope
@Component(
    modules = [
        CommonModule::class,
        TodoDatabaseModule::class,
        TodoDataSourceModule::class,
        TodoMapperModule::class,
        TodoArchiveDataSourceModule::class,
        TodoStartupTasksModule::class
    ],
    dependencies = [AndroidCoreComponent::class]
)
interface TodoComponent : CommonStartupComponent {

    fun exposeTodoWritableDataSource(): Writable.Suspendable.IO<TodoEntity, Response<Unit, TodoErrorContext>>

    fun exposeTodoReadableDataSource(): Readable.IO<Date, Flow<List<TodoEntity>>>

    fun exposeTodoUpdatableDataSource(): Updatable.Suspendable.IO<TodoEntity, Response<Unit, TodoErrorContext>>

    fun exposeTodoDeletableDataSource(): Deletable.Suspendable.IO<TodoEntity, Response<Unit, TodoErrorContext>>

    fun exposeTodoArchiveReadableDataSource(): Readable.Suspendable.IO<Date, ArchivableTodos>

    fun exposeTodoArchiveDeletableDataSource(): Deletable.Suspendable.IO<ArchivableTodos, Response<Unit, TodoArchivableErrorContext>>

    fun exposeMapperDateToLocalDate(): Mapper<Date, LocalDate>
}