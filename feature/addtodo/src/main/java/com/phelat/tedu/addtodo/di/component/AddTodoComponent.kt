package com.phelat.tedu.addtodo.di.component

import com.phelat.tedu.addtodo.di.module.AddTodoViewModelModule
import com.phelat.tedu.addtodo.di.module.SelectedDateDataSourceModule
import com.phelat.tedu.androiddagger.DispatcherComponent
import com.phelat.tedu.androidresource.di.component.AndroidResourceComponent
import com.phelat.tedu.coroutines.di.component.ThreadComponent
import com.phelat.tedu.date.di.component.DateComponent
import com.phelat.tedu.dependencyinjection.feature.FeatureScope
import com.phelat.tedu.todo.di.component.TodoComponent
import dagger.Component

@FeatureScope
@Component(
    modules = [AddTodoViewModelModule::class, SelectedDateDataSourceModule::class],
    dependencies = [
        TodoComponent::class,
        ThreadComponent::class,
        AndroidResourceComponent::class,
        DateComponent::class
    ]
)
interface AddTodoComponent : DispatcherComponent