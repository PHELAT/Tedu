package com.phelat.tedu.navigation.di.component

import com.phelat.tedu.androidcore.di.component.AndroidCoreComponent
import com.phelat.tedu.dependencyinjection.StartupTasks
import com.phelat.tedu.dependencyinjection.library.LibraryScope
import com.phelat.tedu.navigation.di.module.NavigationStartupModule
import com.phelat.tedu.navigation.di.qualifier.NavigationStartupTasks
import dagger.Component

@LibraryScope
@Component(modules = [NavigationStartupModule::class], dependencies = [AndroidCoreComponent::class])
interface NavigationComponent {

    @NavigationStartupTasks
    fun getStartupTasks(): StartupTasks
}