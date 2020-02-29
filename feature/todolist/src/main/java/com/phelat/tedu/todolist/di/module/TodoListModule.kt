package com.phelat.tedu.todolist.di.module

import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.datasource.Updatable
import com.phelat.tedu.lifecycle.viewModelFactory
import com.phelat.tedu.todo.entity.TodoEntity
import com.phelat.tedu.todolist.di.scope.TodoListSubScope
import com.phelat.tedu.todolist.viewmodel.TodoListViewModel
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.flow.Flow

@Module
class TodoListModule {

    @Provides
    @TodoListSubScope
    fun provideTodoListViewModelFactory(
        todoDataSourceUpdatable: Updatable.Suspendable<TodoEntity>,
        todoDataSourceReadable: Readable<Flow<List<TodoEntity>>>
    ) = viewModelFactory {
        TodoListViewModel(todoDataSourceUpdatable, todoDataSourceReadable)
    }
}