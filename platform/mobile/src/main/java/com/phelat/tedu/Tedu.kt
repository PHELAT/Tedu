package com.phelat.tedu

import com.jakewharton.threetenabp.AndroidThreeTen
import com.phelat.tedu.addtodo.di.builder.AddTodoComponentBuilder
import com.phelat.tedu.androidcore.di.builder.AndroidCoreComponentBuilder
import com.phelat.tedu.androiddagger.DaggerAndroidApplication
import com.phelat.tedu.settings.di.builder.SettingsComponentBuilder
import com.phelat.tedu.todolist.di.builder.TodoListComponentBuilder

class Tedu : DaggerAndroidApplication() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        AndroidCoreComponentBuilder.application = this
        installFeature(TodoListComponentBuilder)
        installFeature(AddTodoComponentBuilder)
        installFeature(SettingsComponentBuilder)
        runStartupTasks()
    }
}