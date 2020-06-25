package com.phelat.tedu.todolist.di.module

import androidx.lifecycle.ViewModel
import com.phelat.tedu.lifecycle.ViewModelFactory
import com.phelat.tedu.lifecycle.ViewModelKey
import com.phelat.tedu.lifecycle.ViewModelProviders
import com.phelat.tedu.todolist.di.scope.TodoListScope
import com.phelat.tedu.todolist.viewmodel.TodoListViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface TodoListViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(TodoListViewModel::class)
    @TodoListScope
    fun bindTodoListViewModel(input: TodoListViewModel): ViewModel

    companion object {
        @Provides
        @TodoListScope
        fun provideViewModelFactory(viewModelProviders: ViewModelProviders): ViewModelFactory {
            return ViewModelFactory(viewModelProviders)
        }
    }
}