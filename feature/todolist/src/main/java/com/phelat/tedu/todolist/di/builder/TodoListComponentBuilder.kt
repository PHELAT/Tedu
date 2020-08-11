package com.phelat.tedu.todolist.di.builder

import com.phelat.tedu.analytics.di.builder.AnalyticsComponentBuilder
import com.phelat.tedu.androidresource.di.builder.AndroidResourceComponentBuilder
import com.phelat.tedu.coroutines.di.builder.ThreadComponentBuilder
import com.phelat.tedu.date.di.builder.DateComponentBuilder
import com.phelat.tedu.dependencyinjection.ComponentBuilder
import com.phelat.tedu.dependencyinjection.StartupTasks
import com.phelat.tedu.designsystem.di.builder.DesignSystemComponentBuilder
import com.phelat.tedu.navigation.di.builder.NavigationComponentBuilder
import com.phelat.tedu.todo.di.builder.TodoComponentBuilder
import com.phelat.tedu.todolist.di.component.DaggerTodoListComponent
import com.phelat.tedu.todolist.di.component.TodoListComponent

object TodoListComponentBuilder : ComponentBuilder<TodoListComponent>() {

    override fun initializeComponent(addStartupTask: (StartupTasks) -> Unit): TodoListComponent {
        return DaggerTodoListComponent.builder()
            .threadComponent(ThreadComponentBuilder.getComponent(addStartupTask))
            .todoComponent(TodoComponentBuilder.getComponent(addStartupTask))
            .analyticsComponent(AnalyticsComponentBuilder.getComponent(addStartupTask))
            .dateComponent(DateComponentBuilder.getComponent(addStartupTask))
            .androidResourceComponent(AndroidResourceComponentBuilder.getComponent(addStartupTask))
            .designSystemComponent(DesignSystemComponentBuilder.getComponent(addStartupTask))
            .navigationComponent(NavigationComponentBuilder.getComponent(addStartupTask))
            .build()
    }
}