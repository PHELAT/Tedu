package com.phelat.tedu.todolist.di.module

import com.phelat.tedu.androidresource.ResourceProvider
import com.phelat.tedu.androidresource.input.StringId
import com.phelat.tedu.androidresource.resource.StringResource
import com.phelat.tedu.coroutines.Dispatcher
import com.phelat.tedu.datasource.Deletable
import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.datasource.Updatable
import com.phelat.tedu.datasource.Writable
import com.phelat.tedu.date.TeduDate
import com.phelat.tedu.functional.Response
import com.phelat.tedu.lifecycle.viewModelFactory
import com.phelat.tedu.todo.entity.TodoEntity
import com.phelat.tedu.todo.error.TodoErrorContext
import com.phelat.tedu.todolist.di.scope.TodoListScope
import com.phelat.tedu.todolist.viewmodel.TodoListViewModel
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Module
class TodoListModule {

    @Provides
    @TodoListScope
    fun provideTodoListViewModelFactory(
        dispatcher: Dispatcher,
        todoDataSourceUpdatable: Updatable.Suspendable.IO<TodoEntity, Response<Unit, TodoErrorContext>>,
        todoDataSourceDeletable: Deletable.Suspendable.IO<TodoEntity, Response<Unit, TodoErrorContext>>,
        todoDataSourceWritable: Writable.Suspendable.IO<TodoEntity, Response<Unit, TodoErrorContext>>,
        todoDataSourceReadable: Readable.IO<Date, Flow<List<TodoEntity>>>,
        dateDataSourceReadable: Readable.IO<TeduDate, Date>,
        stringResourceProvider: ResourceProvider<StringId, StringResource>
    ) = viewModelFactory {
        TodoListViewModel(
            dispatcher,
            todoDataSourceUpdatable,
            todoDataSourceDeletable,
            todoDataSourceWritable,
            todoDataSourceReadable,
            dateDataSourceReadable,
            stringResourceProvider
        )
    }
}