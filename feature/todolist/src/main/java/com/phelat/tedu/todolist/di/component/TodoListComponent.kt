package com.phelat.tedu.todolist.di.component

import com.phelat.tedu.androiddagger.DispatcherComponent
import com.phelat.tedu.coroutines.di.component.ThreadComponent
import com.phelat.tedu.todo.di.component.TodoComponent
import com.phelat.tedu.todolist.di.module.TodoListBindingModule
import com.phelat.tedu.todolist.di.scope.TodoListScope
import dagger.Component
import dagger.android.AndroidInjectionModule

@TodoListScope
@Component(
    modules = [AndroidInjectionModule::class, TodoListBindingModule::class],
    dependencies = [TodoComponent::class, ThreadComponent::class]
)
interface TodoListComponent : DispatcherComponent