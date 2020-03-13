package com.phelat.tedu

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.phelat.tedu.addtodo.di.module.AddTodoModule
import com.phelat.tedu.dependencyinjection.installFeatures
import com.phelat.tedu.todolist.di.module.TodoListModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Tedu : Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        startKoin {
            androidContext(this@Tedu)
            installFeatures(TodoListModule, AddTodoModule)
        }
    }
}