package com.phelat.tedu.addtodo.di.builder

import com.phelat.tedu.addtodo.di.component.AddTodoComponent
import com.phelat.tedu.addtodo.di.component.DaggerAddTodoComponent
import com.phelat.tedu.androidresource.di.builder.AndroidResourceComponentBuilder
import com.phelat.tedu.coroutines.di.builder.ThreadComponentBuilder
import com.phelat.tedu.dependencyinjection.ComponentBuilder
import com.phelat.tedu.todo.di.builder.TodoComponentBuilder

object AddTodoComponentBuilder : ComponentBuilder<AddTodoComponent>() {

    override fun initializeComponent(): AddTodoComponent {
        return DaggerAddTodoComponent.builder()
            .threadComponent(ThreadComponentBuilder.getComponent())
            .todoComponent(TodoComponentBuilder.getComponent())
            .androidResourceComponent(AndroidResourceComponentBuilder.getComponent())
            .build()
    }
}