package com.phelat.tedu.addtodo.di.builder

import android.content.Context
import com.phelat.tedu.addtodo.di.component.AddTodoComponent
import com.phelat.tedu.addtodo.di.component.DaggerAddTodoComponent
import com.phelat.tedu.todo.di.builder.TodoComponentBuilder

object AddTodoComponentBuilder {

    @Volatile
    private var component: AddTodoComponent? = null

    fun getComponent(context: Context): AddTodoComponent {
        if (component == null) {
            synchronized(this) {
                if (component == null) {
                    component = DaggerAddTodoComponent.builder()
                        .todoComponent(TodoComponentBuilder.getComponent(context))
                        .build()
                }
            }
        }
        return requireNotNull(component)
    }
}