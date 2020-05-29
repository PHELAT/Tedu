package com.phelat.tedu

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDex
import com.phelat.tedu.addtodo.di.builder.AddTodoComponentBuilder
import com.phelat.tedu.androidcore.di.builder.AndroidCoreComponentBuilder
import com.phelat.tedu.androiddagger.DaggerAndroidApplication
import com.phelat.tedu.backup.di.builder.BackupComponentBuilder
import com.phelat.tedu.settings.di.builder.SettingsComponentBuilder
import com.phelat.tedu.todolist.di.builder.TodoListComponentBuilder

class Tedu : DaggerAndroidApplication() {

    override fun onCreate() {
        super.onCreate()
        AndroidCoreComponentBuilder.application = this
        installFeature(TodoListComponentBuilder)
        installFeature(AddTodoComponentBuilder)
        installFeature(SettingsComponentBuilder)
        installFeature(BackupComponentBuilder)
        runStartupTasks()
        // TODO: create a startup task for this
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}