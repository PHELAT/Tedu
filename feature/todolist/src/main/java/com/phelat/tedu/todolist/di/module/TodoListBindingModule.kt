package com.phelat.tedu.todolist.di.module

import com.phelat.tedu.todolist.di.scope.TodoListScope
import com.phelat.tedu.todolist.view.TodoListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface TodoListBindingModule {

    @TodoListScope
    @ContributesAndroidInjector(modules = [TodoListViewModelModule::class])
    fun bindTodoListFragment(): TodoListFragment
}