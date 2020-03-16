package com.phelat.tedu.todolist.di.builder

import com.phelat.tedu.coroutines.di.builder.ThreadComponentBuilder
import com.phelat.tedu.dependencyinjection.ComponentBuilder
import com.phelat.tedu.todo.di.builder.TodoComponentBuilder
import com.phelat.tedu.todolist.di.component.DaggerTodoListComponent
import com.phelat.tedu.todolist.di.component.TodoListComponent

object TodoListComponentBuilder : ComponentBuilder<TodoListComponent>() {

    override fun initializeComponent(): TodoListComponent {
        return DaggerTodoListComponent.builder()
            .threadComponent(ThreadComponentBuilder.getComponent())
            .todoComponent(TodoComponentBuilder.getComponent())
            .build()
    }
}