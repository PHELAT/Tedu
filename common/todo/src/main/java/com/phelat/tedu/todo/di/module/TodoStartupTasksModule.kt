package com.phelat.tedu.todo.di.module

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.phelat.tedu.dependencyinjection.common.CommonStartupTasks
import com.phelat.tedu.dependencyinjection.scope.CommonScope
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey

@Module
class TodoStartupTasksModule {

    @Provides
    @CommonScope
    @IntoMap
    @StringKey("InitThreeTenABP")
    @CommonStartupTasks
    fun provideInitThreeTenABPStartupTask(application: Application) = Runnable {
        AndroidThreeTen.init(application)
    }
}