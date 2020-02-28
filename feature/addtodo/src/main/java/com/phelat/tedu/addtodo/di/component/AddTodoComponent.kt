package com.phelat.tedu.addtodo.di.component

import com.phelat.tedu.addtodo.di.module.AddTodoBindingModule
import com.phelat.tedu.addtodo.di.scope.AddTodoScope
import com.phelat.tedu.daggerandroid.DispatcherComponent
import com.phelat.tedu.todo.di.component.TodoComponent
import dagger.Component
import dagger.android.AndroidInjectionModule

@AddTodoScope
@Component(
    modules = [AndroidInjectionModule::class, AddTodoBindingModule::class],
    dependencies = [TodoComponent::class]
)
interface AddTodoComponent : DispatcherComponent