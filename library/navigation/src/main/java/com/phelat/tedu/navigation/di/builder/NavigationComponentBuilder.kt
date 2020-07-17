package com.phelat.tedu.navigation.di.builder

import com.phelat.tedu.androidcore.di.builder.AndroidCoreComponentBuilder
import com.phelat.tedu.dependencyinjection.ComponentBuilder
import com.phelat.tedu.dependencyinjection.StartupTasks
import com.phelat.tedu.navigation.di.component.DaggerNavigationComponent
import com.phelat.tedu.navigation.di.component.NavigationComponent

object NavigationComponentBuilder : ComponentBuilder<NavigationComponent>() {

    override fun initializeComponent(addStartupTask: (StartupTasks) -> Unit): NavigationComponent {
        return DaggerNavigationComponent.builder()
            .androidCoreComponent(AndroidCoreComponentBuilder.getComponent(addStartupTask))
            .build()
    }

    override fun getStartupTasks(component: NavigationComponent): StartupTasks? {
        return component.getStartupTasks()
    }
}