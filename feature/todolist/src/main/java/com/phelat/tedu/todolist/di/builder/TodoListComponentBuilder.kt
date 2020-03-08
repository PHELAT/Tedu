package com.phelat.tedu.todolist.di.builder

import android.content.Context
import com.phelat.tedu.todo.di.builder.TodoComponentBuilder
import com.phelat.tedu.todolist.di.component.DaggerTodoListComponent
import com.phelat.tedu.todolist.di.component.TodoListComponent

object TodoListComponentBuilder {

    @Volatile
    private var component: TodoListComponent? = null

    fun getComponent(context: Context): TodoListComponent {
        if (component == null) {
            synchronized(this) {
                if (component == null) {
                    component = DaggerTodoListComponent.builder()
                        .todoComponent(TodoComponentBuilder.getComponent(context))
                        .build()
                }
            }
        }
        return requireNotNull(component)
    }
}