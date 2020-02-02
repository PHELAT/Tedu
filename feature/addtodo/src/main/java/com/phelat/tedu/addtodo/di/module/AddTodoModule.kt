package com.phelat.tedu.addtodo.di.module

import com.phelat.tedu.addtodo.di.scope.AddTodoScope
import com.phelat.tedu.addtodo.view.AddTodoFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AddTodoModule {

    @AddTodoScope
    @ContributesAndroidInjector
    abstract fun bindAddTodoFragment(): AddTodoFragment
}