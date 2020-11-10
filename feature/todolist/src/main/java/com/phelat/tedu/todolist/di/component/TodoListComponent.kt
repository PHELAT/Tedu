package com.phelat.tedu.todolist.di.component

import com.phelat.tedu.analytics.di.component.AnalyticsComponent
import com.phelat.tedu.dependencyinjection.DispatcherComponent
import com.phelat.tedu.androidresource.di.component.AndroidResourceComponent
import com.phelat.tedu.coroutines.di.component.ThreadComponent
import com.phelat.tedu.date.di.component.DateComponent
import com.phelat.tedu.designsystem.di.component.DesignSystemComponent
import com.phelat.tedu.navigation.di.component.NavigationComponent
import com.phelat.tedu.todo.di.component.TodoComponent
import com.phelat.tedu.todolist.di.module.TodoListViewModelModule
import com.phelat.tedu.todolist.di.scope.TodoListScope
import com.phelat.tedu.todolist.view.TodoListFragment
import dagger.Component

@TodoListScope
@Component(
    modules = [
        TodoListViewModelModule::class
    ],
    dependencies = [
        TodoComponent::class,
        ThreadComponent::class,
        AnalyticsComponent::class,
        DateComponent::class,
        AndroidResourceComponent::class,
        DesignSystemComponent::class,
        NavigationComponent::class
    ]
)
interface TodoListComponent : DispatcherComponent {
    fun inject(todoListFragment: TodoListFragment)
}