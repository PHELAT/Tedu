package com.phelat.tedu

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.phelat.tedu.addtodo.di.builder.AddTodoComponentBuilder
import com.phelat.tedu.androidcore.di.builder.AndroidCoreComponentBuilder
import com.phelat.tedu.androiddagger.DaggerAndroidApplication
import com.phelat.tedu.settings.di.builder.SettingsComponentBuilder
import com.phelat.tedu.todolist.di.builder.TodoListComponentBuilder

class Tedu : DaggerAndroidApplication() {

    override fun onCreate() {
        super.onCreate()
        AndroidCoreComponentBuilder.application = this
        installFeature(TodoListComponentBuilder)
        installFeature(AddTodoComponentBuilder)
        installFeature(SettingsComponentBuilder)
        runStartupTasks()
        // TODO: Move this to startup tasks
        FirebaseCrashlytics.getInstance()
            .setCrashlyticsCollectionEnabled(BuildConfig.LOG_CRASHLYTICS)
    }
}