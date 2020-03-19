package com.phelat.tedu.todo.di.component

import com.phelat.tedu.androidcore.di.component.AndroidCoreComponent
import com.phelat.tedu.datasource.Deletable
import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.datasource.Updatable
import com.phelat.tedu.datasource.Writable
import com.phelat.tedu.dependencyinjection.scope.CommonScope
import com.phelat.tedu.mapper.Mapper
import com.phelat.tedu.todo.di.module.TodoArchiveDataSourceModule
import com.phelat.tedu.todo.di.module.TodoDataSourceModule
import com.phelat.tedu.todo.di.module.TodoDatabaseModule
import com.phelat.tedu.todo.di.module.TodoMapperModule
import com.phelat.tedu.todo.entity.TodoEntity
import com.phelat.tedu.todo.type.ArchivableTodos
import dagger.Component
import kotlinx.coroutines.flow.Flow
import org.threeten.bp.LocalDate
import java.util.Date

@CommonScope
@Component(
    modules = [
        TodoDatabaseModule::class,
        TodoDataSourceModule::class,
        TodoMapperModule::class,
        TodoArchiveDataSourceModule::class
    ],
    dependencies = [AndroidCoreComponent::class]
)
interface TodoComponent {

    fun exposeTodoWritableDataSource(): Writable.Suspendable<TodoEntity>

    fun exposeTodoReadableDataSource(): Readable.IO<Date, Flow<List<TodoEntity>>>

    fun exposeTodoUpdatableDataSource(): Updatable.Suspendable<TodoEntity>

    fun exposeTodoDeletableDataSource(): Deletable.Suspendable<TodoEntity>

    fun exposeTodoArchiveReadableDataSource(): Readable.Suspendable.IO<Date, ArchivableTodos>

    // TODO: USE RESULT TYPED CLASS INSTEAD OF BOOLEAN
    fun exposeTodoArchiveDeletableDataSource(): Deletable.Suspendable.IO<ArchivableTodos, Boolean>

    fun exposeMapperDateToLocalDate(): Mapper<Date, LocalDate>
}