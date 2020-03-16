package com.phelat.tedu.todolist.di.component

import com.phelat.tedu.androiddagger.DispatcherComponent
import com.phelat.tedu.coroutines.di.component.ThreadComponent
import com.phelat.tedu.dependencyinjection.scope.FeatureScope
import com.phelat.tedu.todo.di.component.TodoComponent
import com.phelat.tedu.todolist.di.module.TodoListBindingModule
import dagger.Component
import dagger.android.AndroidInjectionModule

@FeatureScope
@Component(
    modules = [AndroidInjectionModule::class, TodoListBindingModule::class],
    dependencies = [TodoComponent::class, ThreadComponent::class]
)
interface TodoListComponent : DispatcherComponent