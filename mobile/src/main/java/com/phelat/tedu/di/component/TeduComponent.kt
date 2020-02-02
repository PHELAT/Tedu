package com.phelat.tedu.di.component

import com.phelat.tedu.Tedu
import com.phelat.tedu.addtodo.di.component.AddTodoComponent
import com.phelat.tedu.addtodo.di.module.AddTodoModule
import com.phelat.tedu.dagger.ApplicationScope
import com.phelat.tedu.todolist.di.component.TodoListComponent
import com.phelat.tedu.todolist.di.module.TodoListModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector

@ApplicationScope
@Component(
    modules = [AndroidInjectionModule::class, AddTodoModule::class, TodoListModule::class],
    dependencies = [AddTodoComponent::class, TodoListComponent::class]
)
interface TeduComponent : AndroidInjector<Tedu>