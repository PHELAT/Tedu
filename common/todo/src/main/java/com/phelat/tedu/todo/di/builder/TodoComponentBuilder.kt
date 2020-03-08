package com.phelat.tedu.todo.di.builder

import android.content.Context
import com.phelat.tedu.core.builder.CoreComponentBuilder
import com.phelat.tedu.todo.di.component.DaggerTodoComponent
import com.phelat.tedu.todo.di.component.TodoComponent

object TodoComponentBuilder {

    @Volatile
    private var component: TodoComponent? = null

    fun getComponent(context: Context): TodoComponent {
        if (component == null) {
            synchronized(this) {
                if (component == null) {
                    component = DaggerTodoComponent.builder()
                        .coreComponent(CoreComponentBuilder.getComponent(context))
                        .build()
                }
            }
        }
        return requireNotNull(component)
    }
}