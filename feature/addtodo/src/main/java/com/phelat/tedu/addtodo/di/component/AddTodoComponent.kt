package com.phelat.tedu.addtodo.di.component

import com.phelat.tedu.addtodo.di.module.AddTodoBindingModule
import com.phelat.tedu.androiddagger.DispatcherComponent
import com.phelat.tedu.androiddagger.StartUpModule
import com.phelat.tedu.androidresource.di.component.AndroidResourceComponent
import com.phelat.tedu.coroutines.di.component.ThreadComponent
import com.phelat.tedu.dependencyinjection.scope.FeatureScope
import com.phelat.tedu.todo.di.component.TodoComponent
import dagger.Component
import dagger.android.AndroidInjectionModule

@FeatureScope
@Component(
    modules = [AndroidInjectionModule::class, StartUpModule::class, AddTodoBindingModule::class],
    dependencies = [TodoComponent::class, ThreadComponent::class, AndroidResourceComponent::class]
)
interface AddTodoComponent : DispatcherComponent