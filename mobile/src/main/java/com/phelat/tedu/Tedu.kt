package com.phelat.tedu

import com.phelat.tedu.addtodo.di.component.AddTodoComponent
import com.phelat.tedu.addtodo.di.component.DaggerAddTodoComponent
import com.phelat.tedu.daggerandroid.DaggerAndroidApplication
import com.phelat.tedu.di.component.DaggerTeduComponent
import com.phelat.tedu.todo.di.component.DaggerTodoComponent
import com.phelat.tedu.todolist.di.component.DaggerTodoListComponent
import com.phelat.tedu.todolist.di.component.TodoListComponent

class Tedu : DaggerAndroidApplication() {

    override fun onCreate() {
        super.onCreate()
        val teduComponent = DaggerTeduComponent.builder()
            .build()
        val todoComponent = DaggerTodoComponent.builder()
            .context(this)
            .build()
        DaggerTodoListComponent.builder()
            .bindTodoComponent(todoComponent)
            .bindDispatcher(teduComponent.dispatcher())
            .build()
            .also { dispatchers += TodoListComponent::class to it.dispatcher() }
        DaggerAddTodoComponent.builder()
            .bindTodoComponent(todoComponent)
            .bindDispatcher(teduComponent.dispatcher())
            .build()
            .also { dispatchers += AddTodoComponent::class to it.dispatcher() }
    }
}