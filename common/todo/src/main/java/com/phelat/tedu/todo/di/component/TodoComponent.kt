package com.phelat.tedu.todo.di.component

import android.content.Context
import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.datasource.Updatable
import com.phelat.tedu.datasource.Writable
import com.phelat.tedu.todo.di.module.TodoBindingModule
import com.phelat.tedu.todo.di.module.TodoDatabaseModule
import com.phelat.tedu.todo.di.scope.TodoScope
import com.phelat.tedu.todo.entity.TodoEntity
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.flow.Flow

@TodoScope
@Component(modules = [TodoBindingModule::class, TodoDatabaseModule::class])
interface TodoComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): TodoComponent
    }

    fun todoWritableDataSource(): Writable<TodoEntity>

    fun todoReadableDataSource(): Readable<Flow<List<TodoEntity>>>

    fun todoUpdatableDataSource(): Updatable<TodoEntity>
}