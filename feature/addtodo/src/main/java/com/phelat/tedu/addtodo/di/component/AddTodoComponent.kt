package com.phelat.tedu.addtodo.di.component

import com.phelat.tedu.addtodo.di.module.AddTodoViewModelModule
import com.phelat.tedu.addtodo.di.module.SelectedDateDataSourceModule
import com.phelat.tedu.addtodo.di.scope.AddTodoScope
import com.phelat.tedu.addtodo.view.AddTodoFragment
import com.phelat.tedu.dependencyinjection.DispatcherComponent
import com.phelat.tedu.androidresource.di.component.AndroidResourceComponent
import com.phelat.tedu.coroutines.di.component.ThreadComponent
import com.phelat.tedu.date.di.component.DateComponent
import com.phelat.tedu.todo.di.component.TodoComponent
import dagger.Component

@AddTodoScope
@Component(
    modules = [AddTodoViewModelModule::class, SelectedDateDataSourceModule::class],
    dependencies = [
        TodoComponent::class,
        ThreadComponent::class,
        AndroidResourceComponent::class,
        DateComponent::class
    ]
)
interface AddTodoComponent : DispatcherComponent {
    fun inject(addTodoFragment: AddTodoFragment)
}