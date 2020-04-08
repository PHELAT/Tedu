package com.phelat.tedu.todo.di.builder

import com.phelat.tedu.androidcore.di.builder.AndroidCoreComponentBuilder
import com.phelat.tedu.dependencyinjection.ComponentBuilder
import com.phelat.tedu.dependencyinjection.StartupTasks
import com.phelat.tedu.todo.di.component.DaggerTodoComponent
import com.phelat.tedu.todo.di.component.TodoComponent

object TodoComponentBuilder : ComponentBuilder<TodoComponent>() {

    override fun initializeComponent(addStartupTask: (StartupTasks) -> Unit): TodoComponent {
        return DaggerTodoComponent.builder()
            .androidCoreComponent(AndroidCoreComponentBuilder.getComponent(addStartupTask))
            .build()
    }

    override fun getStartupTasks(component: TodoComponent): StartupTasks? {
        return component.getTodoStartupTasks()
    }
}