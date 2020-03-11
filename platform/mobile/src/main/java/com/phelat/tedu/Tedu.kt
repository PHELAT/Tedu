package com.phelat.tedu

import android.app.Application
import com.phelat.tedu.addtodo.di.module.AddTodoModule
import com.phelat.tedu.todolist.di.module.TodoListModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Tedu : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Tedu)
            modules(TodoListModule.getModule(), AddTodoModule.getModule())
        }
    }
}