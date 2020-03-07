package com.phelat.tedu.todo.di.component

import com.phelat.tedu.core.component.CoreComponent
import com.phelat.tedu.core.expose.CoreComponentExpose
import com.phelat.tedu.datasource.Deletable
import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.datasource.Updatable
import com.phelat.tedu.datasource.Writable
import com.phelat.tedu.todo.di.module.TodoBindingModule
import com.phelat.tedu.todo.di.module.TodoDatabaseModule
import com.phelat.tedu.todo.di.scope.TodoScope
import com.phelat.tedu.todo.entity.TodoEntity
import dagger.Component
import kotlinx.coroutines.flow.Flow

@TodoScope
@Component(
    modules = [TodoBindingModule::class, TodoDatabaseModule::class],
    dependencies = [CoreComponent::class]
)
interface TodoComponent : CoreComponentExpose {

    fun todoWritableDataSource(): Writable.Suspendable<TodoEntity>

    fun todoReadableDataSource(): Readable<Flow<List<TodoEntity>>>

    fun todoUpdatableDataSource(): Updatable.Suspendable<TodoEntity>

    fun todoDeletableDataSource(): Deletable.Suspendable<TodoEntity>
}