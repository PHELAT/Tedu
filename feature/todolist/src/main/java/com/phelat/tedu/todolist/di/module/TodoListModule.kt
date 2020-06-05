package com.phelat.tedu.todolist.di.module

import com.phelat.tedu.androidresource.ResourceProvider
import com.phelat.tedu.androidresource.input.StringId
import com.phelat.tedu.androidresource.resource.StringResource
import com.phelat.tedu.coroutines.Dispatcher
import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.date.TeduDate
import com.phelat.tedu.lifecycle.viewModelFactory
import com.phelat.tedu.todo.repository.TodoRepository
import com.phelat.tedu.todolist.di.scope.TodoListScope
import com.phelat.tedu.todolist.viewmodel.TodoListViewModel
import dagger.Module
import dagger.Provides
import java.util.Date

@Module
class TodoListModule {

    @Provides
    @TodoListScope
    fun provideTodoListViewModelFactory(
        dispatcher: Dispatcher,
        todoRepository: TodoRepository,
        dateDataSourceReadable: Readable.IO<TeduDate, Date>,
        stringResourceProvider: ResourceProvider<StringId, StringResource>
    ) = viewModelFactory {
        TodoListViewModel(
            dispatcher,
            todoRepository,
            dateDataSourceReadable,
            stringResourceProvider
        )
    }
}