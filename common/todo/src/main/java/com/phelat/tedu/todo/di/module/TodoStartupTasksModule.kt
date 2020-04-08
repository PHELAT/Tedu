package com.phelat.tedu.todo.di.module

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.phelat.tedu.dependencyinjection.StartupTasks
import com.phelat.tedu.dependencyinjection.common.CommonScope
import com.phelat.tedu.todo.di.qualifier.TodoStartupTasks
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.Multibinds
import dagger.multibindings.StringKey

@Module
interface TodoStartupTasksModule {

    @Multibinds
    @TodoStartupTasks
    fun bindStartupTasks(): StartupTasks

    companion object {
        @Provides
        @CommonScope
        @IntoMap
        @StringKey("InitThreeTenABP")
        @TodoStartupTasks
        fun provideInitThreeTenABPStartupTask(application: Application) = Runnable {
            AndroidThreeTen.init(application)
        }
    }
}