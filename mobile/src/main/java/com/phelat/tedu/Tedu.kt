package com.phelat.tedu

import android.app.Application
import com.phelat.tedu.addtodo.di.component.DaggerAddTodoComponent
import com.phelat.tedu.di.component.DaggerTeduComponent
import com.phelat.tedu.todo.di.component.DaggerTodoComponent
import com.phelat.tedu.todolist.di.component.DaggerTodoListComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class Tedu : Application(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        val todoComponent = DaggerTodoComponent.builder()
            .context(this)
            .build()
        val todoListComponent = DaggerTodoListComponent.builder()
            .todoComponent(todoComponent)
            .build()
        val addTodoComponent = DaggerAddTodoComponent.builder()
            .todoComponent(todoComponent)
            .build()
        DaggerTeduComponent.builder()
            .addTodoComponent(addTodoComponent)
            .todoListComponent(todoListComponent)
            .build()
            .inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }

}