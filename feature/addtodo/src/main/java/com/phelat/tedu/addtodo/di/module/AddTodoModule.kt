package com.phelat.tedu.addtodo.di.module

import com.phelat.tedu.addtodo.di.scope.AddTodoSubScope
import com.phelat.tedu.addtodo.view.AddTodoFragment
import com.phelat.tedu.todo.di.module.TodoModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AddTodoModule {

    @AddTodoSubScope
    @ContributesAndroidInjector(modules = [TodoModule::class])
    abstract fun bindAddTodoFragment(): AddTodoFragment
}