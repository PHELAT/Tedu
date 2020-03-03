package com.phelat.tedu.addtodo.di.module

import com.phelat.tedu.addtodo.di.scope.AddTodoSubScope
import com.phelat.tedu.addtodo.viewmodel.AddTodoViewModel
import com.phelat.tedu.coroutines.Dispatcher
import com.phelat.tedu.datasource.Writable
import com.phelat.tedu.lifecycle.viewModelFactory
import com.phelat.tedu.todo.entity.TodoEntity
import dagger.Module
import dagger.Provides

@Module
class AddTodoModule {

    @Provides
    @AddTodoSubScope
    fun provideAddTodoViewModelFactory(
        dispatcher: Dispatcher,
        todoWritableDataSource: Writable.Suspendable<TodoEntity>
    ) = viewModelFactory {
        AddTodoViewModel(dispatcher, todoWritableDataSource)
    }
}