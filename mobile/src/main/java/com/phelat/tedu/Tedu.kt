package com.phelat.tedu

import com.phelat.tedu.addtodo.di.builder.AddTodoComponentBuilder
import com.phelat.tedu.addtodo.di.component.AddTodoComponent
import com.phelat.tedu.daggerandroid.DaggerAndroidApplication
import com.phelat.tedu.todolist.di.builder.TodoListComponentBuilder
import com.phelat.tedu.todolist.di.component.TodoListComponent

class Tedu : DaggerAndroidApplication() {

    override fun onCreate() {
        super.onCreate()
        dispatchers += TodoListComponent::class to TodoListComponentBuilder.getComponent(this)
            .dispatcher()
        dispatchers += AddTodoComponent::class to AddTodoComponentBuilder.getComponent(this)
            .dispatcher()
    }
}