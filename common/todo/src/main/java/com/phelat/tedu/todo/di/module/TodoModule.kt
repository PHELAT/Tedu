package com.phelat.tedu.todo.di.module

import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.datasource.Writable
import com.phelat.tedu.lifecycle.viewModelFactory
import com.phelat.tedu.todo.entity.TodoEntity
import com.phelat.tedu.todo.viewmodel.TodoViewModel
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.flow.Flow

@Module
class TodoModule {

    @Provides
    fun provideTodoViewModelFactory(
        todoWritableDataSource: Writable<TodoEntity>,
        todoReadableDataSource: Readable<Flow<List<TodoEntity>>>
    ) = viewModelFactory {
        TodoViewModel(todoWritableDataSource, todoReadableDataSource)
    }
}