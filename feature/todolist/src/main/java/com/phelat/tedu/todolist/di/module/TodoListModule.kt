package com.phelat.tedu.todolist.di.module

import com.phelat.tedu.todo.di.module.TodoModule
import com.phelat.tedu.todolist.di.scope.TodoListSubScope
import com.phelat.tedu.todolist.view.TodoListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class TodoListModule {

    @TodoListSubScope
    @ContributesAndroidInjector(modules = [TodoModule::class])
    abstract fun bindTodoListFragment(): TodoListFragment
}