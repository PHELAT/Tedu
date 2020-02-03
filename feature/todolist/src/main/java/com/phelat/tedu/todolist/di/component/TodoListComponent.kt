package com.phelat.tedu.todolist.di.component

import com.phelat.tedu.daggerandroid.DispatcherComponent
import com.phelat.tedu.todo.di.component.TodoComponent
import com.phelat.tedu.todolist.di.module.TodoListModule
import com.phelat.tedu.todolist.di.scope.TodoListDispatcherScope
import dagger.Component
import dagger.android.AndroidInjectionModule

@TodoListDispatcherScope
@Component(
    modules = [AndroidInjectionModule::class, TodoListModule::class],
    dependencies = [TodoComponent::class]
)
interface TodoListComponent : DispatcherComponent