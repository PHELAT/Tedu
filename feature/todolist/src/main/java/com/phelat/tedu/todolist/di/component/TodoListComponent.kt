package com.phelat.tedu.todolist.di.component

import com.phelat.tedu.androiddagger.DispatcherComponent
import com.phelat.tedu.androiddagger.FeatureModule
import com.phelat.tedu.coroutines.di.component.ThreadComponent
import com.phelat.tedu.dependencyinjection.common.CommonStartupComponent
import com.phelat.tedu.dependencyinjection.scope.FeatureScope
import com.phelat.tedu.todo.di.component.TodoComponent
import com.phelat.tedu.todolist.di.module.TodoListBindingModule
import dagger.Component

@FeatureScope
@Component(
    modules = [FeatureModule::class, TodoListBindingModule::class],
    dependencies = [TodoComponent::class, ThreadComponent::class]
)
interface TodoListComponent : DispatcherComponent, CommonStartupComponent