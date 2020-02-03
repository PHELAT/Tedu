package com.phelat.tedu

import com.phelat.tedu.addtodo.di.component.DaggerAddTodoComponent
import com.phelat.tedu.di.component.DaggerTeduComponent
import com.phelat.tedu.todo.di.component.DaggerTodoComponent
import com.phelat.tedu.todolist.di.component.DaggerTodoListComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.HasAndroidInjector

class Tedu : DaggerApplication(), HasAndroidInjector {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val todoComponent = DaggerTodoComponent.builder()
            .context(this)
            .build()
        val todoListComponent = DaggerTodoListComponent.builder()
            .todoComponent(todoComponent)
            .build()
        val addTodoComponent = DaggerAddTodoComponent.builder()
            .todoComponent(todoComponent)
            .build()
        return DaggerTeduComponent.builder()
            .addTodoComponent(addTodoComponent)
            .todoListComponent(todoListComponent)
            .build()
    }

}