package com.phelat.tedu.todolist.di.module

import com.phelat.tedu.coroutines.Dispatcher
import com.phelat.tedu.datasource.Deletable
import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.datasource.Updatable
import com.phelat.tedu.datasource.Writable
import com.phelat.tedu.functional.Response
import com.phelat.tedu.lifecycle.viewModelFactory
import com.phelat.tedu.todo.entity.TodoEntity
import com.phelat.tedu.todo.error.TodoErrorContext
import com.phelat.tedu.todo.type.ArchivableTodos
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
        archivableTodoDataSourceReadable: Readable.Suspendable.IO<Date, ArchivableTodos>,
        archivableTodoDataSourceDeletable: Deletable.Suspendable.IO<ArchivableTodos, Boolean>
    ) = viewModelFactory {
        TodoListViewModel(
            dispatcher,
            todoDataSourceUpdatable,
            todoDataSourceDeletable,
            todoDataSourceWritable,
            todoDataSourceReadable,
            archivableTodoDataSourceReadable,
            archivableTodoDataSourceDeletable
        )
    }
}