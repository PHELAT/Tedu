package com.phelat.tedu.todolist.di.module

import com.phelat.tedu.todolist.di.scope.TodoListSubScope
import com.phelat.tedu.todolist.view.TodoListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class TodoListModule {

    @TodoListSubScope
    @ContributesAndroidInjector
    abstract fun bindTodoListFragment(): TodoListFragment
}